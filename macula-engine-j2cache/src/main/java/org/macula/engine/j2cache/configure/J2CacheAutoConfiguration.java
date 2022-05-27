package org.macula.engine.j2cache.configure;

import java.time.Duration;
import java.util.Collections;

import javax.annotation.PostConstruct;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.macula.engine.assistant.support.ApplicationId;
import org.macula.engine.j2cache.enhance.J2CacheManager;
import org.macula.engine.j2cache.properties.J2CacheProperties;
import org.macula.engine.j2cache.properties.J2CacheProperties.CircuitBreakerProperties;
import org.macula.engine.j2cache.stream.CacheUpdateProcessing;
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
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamInfo.XInfoGroups;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer.StreamMessageListenerContainerOptions;

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
		log.debug("[Macula] |- Plugin [J2Cache Plugin] Auto Configure.");
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
	//
	//	@Bean
	//	public RedisMessageListenerContainer J2CacheRedisMessageListenerContainer(J2CacheProperties cacheProperties,
	//			RedisTemplate<Object, Object> redisTemplate, CacheUpdateProcessing j2cacheBroadcastProcessing) {
	//		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
	//		container.setConnectionFactory(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
	//		container.addMessageListener(Objects.requireNonNull(j2cacheBroadcastProcessing), ChannelTopic.of(cacheProperties.getBroadTopic()));
	//
	//		log.debug("[Macula] |- Bean [RedisMessageListenerContainer] Auto Configure.");
	//		return container;
	//	}

	@Bean(destroyMethod = "stop")
	public StreamMessageListenerContainer<String, MapRecord<String, String, String>> j2CacheSubscription(J2CacheProperties cacheProperties,
			RedisConnectionFactory factory, StringRedisTemplate redisTemplate, CacheUpdateProcessing j2cacheBroadcastProcessing,
			ApplicationId applicationId) {
		String key = cacheProperties.getBroadTopic();
		String group = applicationId.getInstanceKey();
		initialRedisGroup(redisTemplate, key, group);
		StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
				.builder().pollTimeout(Duration.ofMillis(100)).build();
		StreamMessageListenerContainer<String, MapRecord<String, String, String>> listenerContainer = StreamMessageListenerContainer.create(factory,
				options);
		listenerContainer.receiveAutoAck(Consumer.from(group, getClass().getName()),
				StreamOffset.create(cacheProperties.getBroadTopic(), ReadOffset.lastConsumed()), j2cacheBroadcastProcessing);
		listenerContainer.start();
		log.debug("[Macula] |- Bean [J2Cache Redis Subscription] Auto Configure.");
		return listenerContainer;
	}

	private void initialRedisGroup(StringRedisTemplate redisTemplate, String key, String group) {
		StreamOperations<String, Object, Object> streamOperations = redisTemplate.opsForStream();
		if (!redisTemplate.hasKey(key)) {
			RecordId recordId = streamOperations.add(key, Collections.singletonMap(key, group));
			redisTemplate.opsForStream().delete(key, recordId);
			log.trace("[Macula] |- J2CACHE - Create Redis Stream Key [{}]", key);
		}
		XInfoGroups groups = streamOperations.groups(key);
		if (groups.stream().noneMatch(s -> s.groupName().equals(group))) {
			streamOperations.createGroup(key, group);
			log.trace("[Macula] |- J2CACHE - Create Redis Stream Group [{}] of [{}]", group, key);
		}
	}

	@Bean
	@ConditionalOnMissingBean
	public CacheUpdateProcessing j2cacheBroadcastProcessing(StringRedisTemplate messageRedisTemplate, J2CacheProperties cacheProperties,
			CaffeineCacheManager cacheManager) {
		CacheUpdateProcessing bean = new CacheUpdateProcessing(messageRedisTemplate, cacheProperties.getBroadTopic(), cacheManager);
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
				.onError(event -> log.trace("[Macula] |- J2CACHE - Cache circuit breaker error occurred in [{}] ", event.getElapsedDuration(),
						event.getThrowable()))
				.onSlowCallRateExceeded(event -> log.trace("[Macula] |- J2CACHE - Cache circuit breaker [{}] calls were slow, rate exceeded",
						event.getSlowCallRate()))
				.onFailureRateExceeded(
						event -> log.trace("[Macula] |- J2CACHE - Cache circuit breaker [{}] calls failed, rate exceeded", event.getFailureRate()))
				.onStateTransition(event -> log.trace("[Macula] |- J2CACHE - Cache circuit breaker [{}] state transitioned from [{}] to [{}]",
						event.getCircuitBreakerName(), event.getStateTransition().getFromState(), event.getStateTransition().getToState()));

		log.debug("[Macula] |- Bean [CircuitBreaker] Auto Configure.");
		return cb;
	}
}
