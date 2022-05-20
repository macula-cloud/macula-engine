package org.macula.engine.j2cache.enhance;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.macula.engine.j2cache.event.CacheUpdateProcessing;
import org.macula.engine.j2cache.properties.Expire;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.lang.Nullable;

/**
 * <p>Description: 自定义多级缓存管理器 </p>
 *
 */
@Slf4j
public class J2CacheManager implements CacheManager {

	private RedisCacheManager redisCacheManager;
	private CaffeineCacheManager caffeineCacheManager;
	private boolean desensitization = true;
	private boolean clearRemoteOnExit = false;
	private boolean allowNullValues = true;
	private Map<String, Expire> expires = new HashMap<>();

	private boolean dynamic = true;

	private final Map<String, Cache> cacheMap = new ConcurrentHashMap<>(16);

	private boolean openCircuitBreaker;
	private CircuitBreaker cacheCircuitBreaker;
	private CacheUpdateProcessing broadcastProcessing;

	public J2CacheManager() {
	}

	public J2CacheManager(String... cacheNames) {
		setCacheNames(Arrays.asList(cacheNames));
	}

	public void setRedisCacheManager(RedisCacheManager redisCacheManager) {
		this.redisCacheManager = redisCacheManager;
	}

	public void setCaffeineCacheManager(CaffeineCacheManager caffeineCacheManager) {
		this.caffeineCacheManager = caffeineCacheManager;
	}

	public void setExpires(Map<String, Expire> expires) {
		this.expires = expires;
	}

	public Map<String, Expire> getExpires() {
		return this.expires;
	}

	public void setDesensitization(boolean desensitization) {
		this.desensitization = desensitization;
	}

	public void setClearRemoteOnExit(boolean clearRemoteOnExit) {
		this.clearRemoteOnExit = clearRemoteOnExit;
	}

	public boolean isAllowNullValues() {
		return allowNullValues;
	}

	public void setAllowNullValues(boolean allowNullValues) {
		this.allowNullValues = allowNullValues;
	}

	/**
	 * Specify the set of cache names for this CacheManager's 'static' mode.
	 * <p>The number of caches and their names will be fixed after a call to this method,
	 * with no creation of further cache regions at runtime.
	 * <p>Calling this with a {@code null} collection argument resets the
	 * mode to 'dynamic', allowing for further creation of caches again.
	 */
	public void setCacheNames(@Nullable Collection<String> cacheNames) {
		if (cacheNames != null) {
			for (String name : cacheNames) {
				this.cacheMap.put(name, createJ2Cache(name));
			}
			this.dynamic = false;
		} else {
			this.dynamic = true;
		}
	}

	protected Cache createJ2Cache(String name) {
		CaffeineCache caffeineCache = (CaffeineCache) this.caffeineCacheManager.getCache(name);
		RedisCache redisCache = (RedisCache) this.redisCacheManager.getCache(name);
		log.debug("[Macula] |- J2CACHE - J2Cache [{}] is CREATED.", name);
		return new J2Cache(name, caffeineCache, redisCache, desensitization, clearRemoteOnExit, isAllowNullValues(),
				openCircuitBreaker, cacheCircuitBreaker, broadcastProcessing);
	}

	@Override
	@Nullable
	public Cache getCache(String name) {
		return this.cacheMap.computeIfAbsent(name, cacheName -> this.dynamic ? createJ2Cache(cacheName) : null);
	}

	@Override
	public Collection<String> getCacheNames() {
		return Collections.unmodifiableSet(this.cacheMap.keySet());
	}

	/**
	 * @param openCircuitBreaker the openCircuitBreaker to set
	 */
	public void setOpenCircuitBreaker(boolean openCircuitBreaker) {
		this.openCircuitBreaker = openCircuitBreaker;
	}

	/**
	 * @param cacheCircuitBreaker the cacheCircuitBreaker to set
	 */
	public void setCacheCircuitBreaker(CircuitBreaker cacheCircuitBreaker) {
		this.cacheCircuitBreaker = cacheCircuitBreaker;
	}

	/**
	 * @param broadcastProcessing the broadcastProcessing to set
	 */
	public void setBroadcastProcessing(CacheUpdateProcessing broadcastProcessing) {
		this.broadcastProcessing = broadcastProcessing;
	}
}
