package org.macula.engine.j2cache.enhance;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.macula.engine.assistant.constants.SymbolConstants;
import org.macula.engine.j2cache.properties.Expire;
import org.macula.engine.j2cache.properties.J2CacheProperties;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.util.StringUtils;

/**
 * <p>Extension RedisCacheManager </p>
 * <p>Support Cache Value  ttl </p>
 */
@Slf4j
public class J2RedisCacheManager extends RedisCacheManager {

	private J2CacheProperties cacheProperties;

	public J2RedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
			J2CacheProperties cacheProperties) {
		super(cacheWriter, defaultCacheConfiguration);
		this.cacheProperties = cacheProperties;
	}

	public J2RedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
			J2CacheProperties cacheProperties, String... initialCacheNames) {
		super(cacheWriter, defaultCacheConfiguration, initialCacheNames);
		this.cacheProperties = cacheProperties;
	}

	public J2RedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
			boolean allowInFlightCacheCreation, J2CacheProperties cacheProperties, String... initialCacheNames) {
		super(cacheWriter, defaultCacheConfiguration, allowInFlightCacheCreation, initialCacheNames);
		this.cacheProperties = cacheProperties;
	}

	public J2RedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
			Map<String, RedisCacheConfiguration> initialCacheConfigurations, J2CacheProperties cacheProperties) {
		super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations);
		this.cacheProperties = cacheProperties;
	}

	public J2RedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
			Map<String, RedisCacheConfiguration> initialCacheConfigurations, boolean allowInFlightCacheCreation) {
		super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
	}

	@Override
	protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
		Map<String, Expire> expires = cacheProperties.getExpires();
		if (MapUtils.isNotEmpty(expires)) {
			String key = StringUtils.replace(name, SymbolConstants.COLON, cacheProperties.getSeparator());
			if (expires.containsKey(key)) {
				Expire expire = expires.get(key);
				log.debug("[Macula] |- J2CACHE - Redis cache [{}] is setted to use CUSTEM exprie.", name);
				cacheConfig = cacheConfig.entryTtl(expire.getTtl());
			}
		}

		return super.createRedisCache(name, cacheConfig);
	}
}
