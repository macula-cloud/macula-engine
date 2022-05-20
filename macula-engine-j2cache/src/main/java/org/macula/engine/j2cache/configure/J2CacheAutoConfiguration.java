package org.macula.engine.j2cache.configure;

import java.util.Objects;

import javax.annotation.PostConstruct;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.macula.engine.j2cache.enhance.J2CacheManager;
import org.macula.engine.j2cache.event.CacheUpdateProcessing;
import org.macula.engine.j2cache.properties.J2CacheProperties;
import org.macula.engine.j2cache.properties.J2CacheProperties.CircuitBreakerProperties;
import org.macula.engine.j2cache.utils.J2CacheUtils;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * <p>Custom Two Level Cache Configuration</p>
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(J2CacheProperties.class)
@Import({
		CaffeineConfiguration.class,
		RedisConfiguration.class })
@EnableCaching
@Slf4j
public class J2CacheAutoConfiguration {

	public static final String CIRCUIT_BREAKER_NAME = "J2CacheCircuitBreaker";
	public static final String CIRCUIT_BREAKER_CONFIGURATION_NAME = "J2CacheCircuitBreakerConfiguration";

	@PostConstruct
	public void postConstruct() {
		log.debug("[Macula] |- Plugin [Engine J2Cache] Auto Configure.");
	}

	@Bean
	@Primary
	@ConditionalOnMissingBean
	public J2CacheManager j2CacheManager(CaffeineCacheManager caffeineCacheManager, RedisCacheManager redisCacheManager,
			J2CacheProperties cacheProperties, CacheUpdateProcessing j2cacheBroadcastProcessing) {
		J2CacheManager j2CacheManager = new J2CacheManager();
		j2CacheManager.setCaffeineCacheManager(caffeineCacheManager);
		j2CacheManager.setRedisCacheManager(redisCacheManager);
		j2CacheManager.setDesensitization(cacheProperties.getDesensitization());
		j2CacheManager.setClearRemoteOnExit(cacheProperties.getClearRemoteOnExit());
		j2CacheManager.setAllowNullValues(cacheProperties.getAllowNullValues());

		j2CacheManager.setOpenCircuitBreaker(cacheProperties.isOpenCircuitBreaker());
		j2CacheManager.setCacheCircuitBreaker(createCacheCircuitBreaker(cacheProperties));
		j2CacheManager.setBroadcastProcessing(j2cacheBroadcastProcessing);

		J2CacheUtils.setCacheManager(j2CacheManager);
		log.debug("[Macula] |- Bean [J2CacheManager] Auto Configure.");
		return j2CacheManager;
	}

	@Bean
	public RedisMessageListenerContainer J2CacheRedisMessageListenerContainer(J2CacheProperties cacheProperties,
			RedisTemplate<Object, Object> redisTemplate, CacheUpdateProcessing j2cacheBroadcastProcessing) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
		container.addMessageListener(Objects.requireNonNull(j2cacheBroadcastProcessing),
				ChannelTopic.of(cacheProperties.getBroadTopic()));

		log.debug("[Macula] |- Bean [RedisMessageListenerContainer] Auto Configure.");
		return container;
	}

	@Bean
	@ConditionalOnMissingBean
	public CacheUpdateProcessing j2cacheBroadcastProcessing(RedisTemplate<Object, Object> messageRedisTemplate,
			J2CacheProperties cacheProperties, CaffeineCacheManager cacheManager) {
		CacheUpdateProcessing bean = new CacheUpdateProcessing(messageRedisTemplate, cacheProperties.getBroadTopic(),
				cacheManager);
		log.debug("[Macula] |- Bean [CacheUpdateProcessing] Auto Configure.");
		return bean;
	}

	private CircuitBreaker createCacheCircuitBreaker(J2CacheProperties cacheProperties) {
		CircuitBreakerRegistry cbr = CircuitBreakerRegistry.ofDefaults();

		if (!cbr.getConfiguration(CIRCUIT_BREAKER_CONFIGURATION_NAME).isPresent()) {
			CircuitBreakerProperties props = cacheProperties.getCircuitBreaker();

			CircuitBreakerConfig.Builder cbc = CircuitBreakerConfig.custom();
			cbc.failureRateThreshold(props.getFailureRateThreshold());
			cbc.slowCallRateThreshold(props.getSlowCallRateThreshold());
			cbc.slowCallDurationThreshold(props.getSlowCallDurationThreshold());
			cbc.permittedNumberOfCallsInHalfOpenState(props.getPermittedNumberOfCallsInHalfOpenState());
			cbc.maxWaitDurationInHalfOpenState(props.getMaxWaitDurationInHalfOpenState());
			cbc.slidingWindowType(props.getSlidingWindowType());
			cbc.slidingWindowSize(props.getSlidingWindowSize());
			cbc.minimumNumberOfCalls(props.getMinimumNumberOfCalls());
			cbc.waitDurationInOpenState(props.getWaitDurationInOpenState());
			cbr.addConfiguration(CIRCUIT_BREAKER_CONFIGURATION_NAME, cbc.build());
		}

		CircuitBreaker cb = cbr.circuitBreaker(CIRCUIT_BREAKER_NAME, CIRCUIT_BREAKER_CONFIGURATION_NAME);
		cb.getEventPublisher()
				.onError(event -> log.trace("[Macula] |- J2CACHE - Cache circuit breaker error occurred in [{}] ",
						event.getElapsedDuration(), event.getThrowable()))
				.onSlowCallRateExceeded(event -> log.trace(
						"[Macula] |- J2CACHE - Cache circuit breaker [{}] calls were slow, rate exceeded",
						event.getSlowCallRate()))
				.onFailureRateExceeded(event -> log.trace(
						"[Macula] |- J2CACHE - Cache circuit breaker [{}] calls failed, rate exceeded",
						event.getFailureRate()))
				.onStateTransition(event -> log.trace(
						"[Macula] |- J2CACHE - Cache circuit breaker [{}] state transitioned from [{}] to [{}]",
						event.getCircuitBreakerName(), event.getStateTransition().getFromState(),
						event.getStateTransition().getToState()));

		log.debug("[Macula] |- Bean [CircuitBreaker] Auto Configure.");
		return cb;
	}
}
