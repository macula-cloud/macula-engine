package org.macula.engine.j2cache.enhance;

import java.util.concurrent.Callable;

import cn.hutool.crypto.SecureUtil;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.macula.engine.j2cache.stream.CacheUpdateProcessing;

import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.cache.support.NullValue;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * <p>Two Level Cache </p>
 */
@Slf4j
public class J2Cache extends AbstractValueAdaptingCache {

	private final String name;
	private final CaffeineCache caffeineCache;
	private final RedisCache redisCache;
	private final boolean desensitization;
	private final boolean clearRemoteOnExit;
	private final boolean openCircuitBreaker;
	private final CircuitBreaker cacheCircuitBreaker;
	private final CacheUpdateProcessing broadcastProcessing;

	public J2Cache(String name, CaffeineCache caffeineCache, RedisCache redisCache, boolean desensitization,
			boolean clearRemoteOnExit, boolean allowNullValues, boolean openCircuitBreaker,
			CircuitBreaker cacheCircuitBreaker, CacheUpdateProcessing broadcastProcessing) {
		super(allowNullValues);
		this.name = name;
		this.caffeineCache = caffeineCache;
		this.redisCache = redisCache;
		this.desensitization = desensitization;
		this.clearRemoteOnExit = clearRemoteOnExit;
		this.openCircuitBreaker = openCircuitBreaker;
		this.cacheCircuitBreaker = cacheCircuitBreaker;
		this.broadcastProcessing = broadcastProcessing;
	}

	private String secure(Object key) {
		String original = String.valueOf(key);
		if (desensitization) {
			if (StringUtils.startsWithIgnoreCase(original, "sql:")) {
				String recent = SecureUtil.md5(original);
				log.trace("[Macula] |- J2CACHE - Secure the sql type key [{}] to [{}]", original, recent);
				return recent;
			}
		}
		return original;
	}

	@Override
	protected Object lookup(Object key) {
		String secure = secure(key);

		Object caffeineValue = caffeineCache.get(secure);
		if (!ObjectUtils.isEmpty(caffeineValue)) {
			log.trace("[Macula] |- J2CACHE - Found the cache in caffeine cache, value is : [{}]", caffeineValue);
			return caffeineValue;
		}

		Object redisValue = this.doRedisOperation(() -> redisCache.get(secure));
		if (!ObjectUtils.isEmpty(redisValue)) {
			log.trace("[Macula] |- J2CACHE - Found the cache in redis cache, value is : [{}]", redisValue);
			return redisValue;
		}

		log.trace("[Macula] |- J2CACHE - Lookup the cache for key: [{}], value is null", secure);

		return null;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Object getNativeCache() {
		return this;
	}

	/**
	 * 查询二级缓存
	 *
	 * @param key
	 * @param valueLoader
	 * @return
	 */
	private <T> Object getRedisStoreValue(Object key, Callable<T> valueLoader) {
		T value = redisCache.get(key, valueLoader);
		log.trace("[Macula] |- J2CACHE - Get <T> with valueLoader form redis cache, hit the cache.");
		return toStoreValue(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Nullable
	public <T> T get(Object key, Callable<T> valueLoader) {
		String secure = secure(key);

		T value = (T) caffeineCache.getNativeCache().get(secure,
				k -> this.doRedisOperation(() -> getRedisStoreValue(k, valueLoader)));
		if (value instanceof NullValue) {
			log.trace("[Macula] |- J2CACHE - Get <T> with type form valueLoader Cache for key: [{}], value is null",
					secure);
			return null;
		}

		return value;
	}

	@Override
	public void put(Object key, Object value) {
		if (!isAllowNullValues() && value == null) {
			throw new IllegalArgumentException(String.format(
					"Cache '%s' does not allow 'null' values. Avoid storing null via '@Cacheable(unless=\"#result == null\")' or configure RedisCache to allow 'null' via RedisCacheConfiguration.",
					name));
		} else {
			String secure = secure(key);
			log.trace("[Macula] |- J2CACHE - Put data into  Caffeine Cache, with key: [{}] and value: [{}]", secure,
					value);
			caffeineCache.put(secure, value);
			log.trace("[Macula] |- J2CACHE - Put data into  Redis Cache, with key: [{}] and value: [{}]", secure,
					value);
			doRedisOperation(() -> redisCache.put(secure, value));

			sendBroadcastMessage(secure);
		}
	}

	@Override
	public void evict(Object key) {
		String secure = secure(key);

		log.trace("[Macula] |- J2CACHE - Evict J2Cache for key: {}", secure);

		// 删除的时候要先删除二级缓存再删除一级缓存，否则有并发问题
		this.doRedisOperation(() -> redisCache.evict(secure));
		log.trace("[Macula] |- J2CACHE - Evict J2 Cache in redis cache, key: {}", secure);

		caffeineCache.evict(secure);
		log.trace("[Macula] |- J2CACHE - Evict J2 Cache in caffeine cache, key: {}", secure);

		sendBroadcastMessage(secure);

	}

	@Override
	public void clear() {
		log.trace("[Macula] |- J2CACHE - Clear J2Cache.");

		if (clearRemoteOnExit) {
			redisCache.clear();
			log.trace("[Macula] |- J2CACHE - Clear J2Cache in redis cache.");
		}

		caffeineCache.clear();
		log.trace("[Macula] |- J2CACHE - Clear J2Cache in caffeine cache.");

		sendBroadcastMessage(null);
	}

	@Override
	public ValueWrapper get(Object key) {

		String secure = secure(key);

		ValueWrapper caffeineValue = caffeineCache.get(secure);
		if (!ObjectUtils.isEmpty(caffeineValue)) {
			log.trace("[Macula] |- J2CACHE - Get ValueWrapper data from caffeine cache, hit the cache.");
			return caffeineValue;
		}

		ValueWrapper redisValue = redisCache.get(secure);
		if (!ObjectUtils.isEmpty(redisValue)) {
			log.trace("[Macula] |- J2CACHE - Get ValueWrapper data from redis cache, hit the cache.");
			caffeineCache.put(secure, redisValue.get());
			log.trace("[Macula] |- J2CACHE - Put data into  Caffeine Cache, with key: [{}] and value: [{}]", secure,
					redisValue.get());
			return redisValue;
		}

		log.trace("[Macula] |- J2CACHE - Get ValueWrapper data form J2Cache for key: [{}], value is null", secure);

		return null;
	}

	/** @param call to Redis */
	private void doRedisOperation(@NonNull Runnable call) {
		if (openCircuitBreaker) {
			Try.runRunnable(cacheCircuitBreaker.decorateRunnable(call));
		} else {
			Try.runRunnable(call);
		}
	}

	/**
	 * @param call to Redis
	 * @return execution result as {@link Try}
	 */
	private <T> Try<T> doRedisOperation(@NonNull CheckedFunction0<T> call) {
		if (openCircuitBreaker) {
			log.trace("[Macula] |- J2CACHE - Redis Cache Operation in CircuitBreaker [{}]", call);
			return Try.of(cacheCircuitBreaker.decorateCheckedSupplier(call));
		} else {
			return Try.of(call);
		}
	}

	/** @param key to send notification about eviction. Can be {@code null}. */
	private void sendBroadcastMessage(@Nullable String key) {
		if (openCircuitBreaker) {
			log.trace("[Macula] |- J2CACHE - Redis Cache Broadcast Message in CircuitBreaker, key-> [{}]", key);
			Try.runRunnable(
					cacheCircuitBreaker.decorateRunnable(() -> broadcastProcessing.sendBroadcast(getName(), key)));
		} else {
			log.trace("[Macula] |- J2CACHE - Redis Cache Broadcast Message, key-> [{}]", key);
			Try.runRunnable(() -> broadcastProcessing.sendBroadcast(getName(), key));
		}
	}
}
