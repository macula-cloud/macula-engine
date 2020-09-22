package org.macula.cloud.cache.configure;

import org.macula.cloud.cache.MultiCacheAutoConfig;
import org.macula.cloud.cache.utils.J2CacheUtils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class CloudCacheAutoConfiguration extends MultiCacheAutoConfig {

	@Bean
	public J2CacheUtils j2CacheUtils(CacheManager cacheManager) {
		J2CacheUtils.setCacheManager(cacheManager);
		return new J2CacheUtils();
	}

}
