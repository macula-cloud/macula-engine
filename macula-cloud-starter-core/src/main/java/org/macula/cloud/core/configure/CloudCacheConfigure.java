package org.macula.cloud.core.configure;

import org.macula.cloud.core.utils.J2CacheUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.oschina.j2cache.CacheChannel;

@Configuration
@ConditionalOnProperty(prefix = "macula.cloud", name = "cache.j2cache", havingValue = "true", matchIfMissing = true)
public class CloudCacheConfigure {

	@Bean
	public J2CacheUtils j2CacheUtils(CacheChannel cacheChannel) {
		J2CacheUtils.setCacheChannel(cacheChannel);
		return new J2CacheUtils();	
	}

}
