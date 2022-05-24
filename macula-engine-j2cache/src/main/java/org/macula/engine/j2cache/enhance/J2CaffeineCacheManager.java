package org.macula.engine.j2cache.enhance;

import java.util.Map;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.macula.engine.assistant.constants.SymbolConstants;
import org.macula.engine.j2cache.properties.Expire;
import org.macula.engine.j2cache.properties.J2CacheProperties;

import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.util.StringUtils;

/**
 * <p>Custom CaffeineCacheManager </p>
 */
@Slf4j
public class J2CaffeineCacheManager extends CaffeineCacheManager {

	private final J2CacheProperties cacheProperties;

	public J2CaffeineCacheManager(J2CacheProperties cacheProperties) {
		this.cacheProperties = cacheProperties;
		this.setAllowNullValues(cacheProperties.getAllowNullValues());
	}

	public J2CaffeineCacheManager(J2CacheProperties cacheProperties, String... cacheNames) {
		super(cacheNames);
		this.cacheProperties = cacheProperties;
		this.setAllowNullValues(cacheProperties.getAllowNullValues());
	}

	@Override
	protected Cache<Object, Object> createNativeCaffeineCache(String name) {
		Map<String, Expire> expires = cacheProperties.getExpires();
		if (MapUtils.isNotEmpty(expires)) {
			String key = StringUtils.replace(name, SymbolConstants.COLON, cacheProperties.getSeparator());
			if (expires.containsKey(key)) {
				Expire expire = expires.get(key);
				log.debug("[Macula] |- J2CACHE - Caffeine cache [{}] is setted to use CUSTEM exprie.", name);
				return Caffeine.newBuilder().expireAfterWrite(expire.getDuration(), expire.getUnit()).build();
			}
		}

		return super.createNativeCaffeineCache(name);
	}
}
