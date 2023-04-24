package org.macula.engine.j2cache.configure;

import jakarta.annotation.PostConstruct;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.macula.engine.j2cache.enhance.J2CaffeineCacheManager;
import org.macula.engine.j2cache.properties.J2CacheProperties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Custom Caffeine Configuration </p>
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class CaffeineConfiguration {

	@PostConstruct
	public void postConstruct() {
		log.debug("[Macula] |- Plugin [Engine J2Cache Caffeine] Auto Configure.");
	}

	@Bean
	public Caffeine<Object, Object> caffeine(J2CacheProperties j2cacheProperties) {
		Caffeine<Object, Object> caffeine = Caffeine.newBuilder().expireAfterWrite(j2cacheProperties.getDuration(),
				j2cacheProperties.getUnit());

		log.debug("[Macula] |- Bean [Caffeine] Auto Configure.");

		return caffeine;
	}

	@Bean
	@ConditionalOnMissingBean(CaffeineCacheManager.class)
	public CaffeineCacheManager caffeineCacheManager(Caffeine<Object, Object> caffeine,
			J2CacheProperties j2cacheProperties) {
		J2CaffeineCacheManager j2CaffeineCacheManager = new J2CaffeineCacheManager(j2cacheProperties);
		j2CaffeineCacheManager.setCaffeine(caffeine);
		log.debug("[Macula] |- Bean [Caffeine Cache Manager] Auto Configure.");
		return j2CaffeineCacheManager;
	}
}
