package org.macula.engine.j2cache.configure;

import jakarta.annotation.PostConstruct;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.macula.engine.j2cache.enhance.J2RedisCacheManager;
import org.macula.engine.j2cache.properties.J2CacheProperties;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis Cache Configuration
 */
@Slf4j
@AllArgsConstructor
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(RedisAutoConfiguration.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfiguration {

	private RedisSerializer<String> keySerializer() {
		return new StringRedisSerializer();
	}

	private RedisSerializer<?> valueSerializer() {
		return new Jackson2JsonRedisSerializer<>(Object.class);
	}

	@PostConstruct
	public void postConstruct() {
		log.debug("[Macula] |- Plugin [Engine J2Cache Redis] Auto Configure.");
	}

	/**
	 * 重新配置一个RedisTemplate
	 *
	 * @return {@link RedisTemplate}
	 */
	@Bean(name = "redisTemplate")
	@ConditionalOnMissingBean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(keySerializer());
		redisTemplate.setHashKeySerializer(keySerializer());
		redisTemplate.setHashValueSerializer(valueSerializer());
		//		redisTemplate.setValueSerializer(valueSerializer());
		//		redisTemplate.setDefaultSerializer(valueSerializer());

		redisTemplate.afterPropertiesSet();

		log.debug("[Macula] |- Bean [RedisTemplate] Auto Configure.");

		return redisTemplate;
	}

	@Bean(name = "stringRedisTemplate")
	@ConditionalOnMissingBean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
		stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
		stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
		stringRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
		//		stringRedisTemplate.setValueSerializer(valueSerializer());
		stringRedisTemplate.afterPropertiesSet();

		log.debug("[Macula] |- Bean [StringRedisTemplate] Auto Configure.");

		return stringRedisTemplate;
	}

	@Bean
	@ConditionalOnMissingBean(RedisCacheManager.class)
	public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory,
			J2CacheProperties j2cacheProperties) {
		RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);

		// 注意：这里 RedisCacheConfiguration 每一个方法调用之后，都会返回一个新的 RedisCacheConfiguration 对象，所以要注意对象的引用关系。
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(j2cacheProperties.getTtl());

		boolean allowNullValues = j2cacheProperties.getAllowNullValues();
		if (!allowNullValues) {
			// 注意：这里 RedisCacheConfiguration 每一个方法调用之后，都会返回一个新的 RedisCacheConfiguration 对象，所以要注意对象的引用关系。
			redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues();
		}

		J2RedisCacheManager j2RedisCacheManager = new J2RedisCacheManager(redisCacheWriter, redisCacheConfiguration,
				j2cacheProperties);
		j2RedisCacheManager.setTransactionAware(false);
		j2RedisCacheManager.afterPropertiesSet();

		log.debug("[Macula] |- Bean [RedisCacheManager] Auto Configure.");

		return j2RedisCacheManager;
	}
}
