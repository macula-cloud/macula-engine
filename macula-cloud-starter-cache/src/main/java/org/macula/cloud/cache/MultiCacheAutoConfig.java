package org.macula.cloud.cache;

import java.util.List;
import java.util.Optional;

import org.macula.cloud.cache.l1.L1CacheManager;
import org.macula.cloud.cache.l1.caffeine.CaffeineL1CacheManager;
import org.macula.cloud.cache.l2.L2CacheManager;
import org.macula.cloud.cache.l2.redis.RedisL2CacheManager;
import org.macula.cloud.cache.multi.MultiCacheManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * redis config copy from RedisAutoConfiguration and RedisCacheConfiguration
 */
@EnableConfigurationProperties({ MultiCacheProperties.class, RedisProperties.class })
public class MultiCacheAutoConfig {

	@Bean
	@ConditionalOnMissingBean
	public CacheManagerCustomizers cacheManagerCustomizers(ObjectProvider<List<CacheManagerCustomizer<?>>> customizers) {
		return new CacheManagerCustomizers(customizers.getIfAvailable());
	}

	@Bean
	@Primary
	public MultiCacheManager multiCacheManager(Optional<L2CacheManager> l2CacheManagerOptional, MultiCacheProperties multiCacheProperties) {
		L1CacheManager l1CacheManager = null;
		if (multiCacheProperties.getL1().isEnabled()) {
			if (CaffeineL1CacheManager.type().equals(multiCacheProperties.getL1().getType())) {
				l1CacheManager = new CaffeineL1CacheManager();
				((CaffeineL1CacheManager) l1CacheManager).setAllowNullValues(multiCacheProperties.isAllowNullValues());
			}
		}
		L2CacheManager l2CacheManager = null;
		if (l2CacheManagerOptional.isPresent()) {
			l2CacheManager = l2CacheManagerOptional.get();
		}
		return new MultiCacheManager(l1CacheManager, l2CacheManager, multiCacheProperties);
	}

	@Bean
	@ConditionalOnMissingBean(L2CacheManager.class)
	public L2CacheManager cacheManager(RedisConnectionFactory connectionFactory, CacheManagerCustomizers customizerInvoker) {
		return customizerInvoker.customize(new RedisL2CacheManager(connectionFactory));
	}

}
