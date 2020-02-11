package org.macula.cloud.sdk.configure;

import org.macula.cloud.sdk.utils.J2CacheUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import net.oschina.j2cache.CacheChannel;

@Configuration
@ConditionalOnProperty(prefix = "macula.cloud", name = "cache.j2cache", havingValue = "true", matchIfMissing = false)
@ImportResource(locations = { "classpath:j2cache-beans.xml" })
public class CloudCacheConfigure {

	@Bean
	public J2CacheUtils j2CacheUtils(CacheChannel cacheChannel) {
		J2CacheUtils.setCacheChannel(cacheChannel);
		return new J2CacheUtils();
	}

}
