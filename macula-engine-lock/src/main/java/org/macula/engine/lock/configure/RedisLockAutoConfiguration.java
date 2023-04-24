package org.macula.engine.lock.configure;

import jakarta.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.macula.engine.lock.aspectj.RedisLockAop;
import org.redisson.api.RedissonClient;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式锁自动配置器
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "macula.lock", name = "redis", havingValue = "true", matchIfMissing = true)
public class RedisLockAutoConfiguration {

	@PostConstruct
	public void postConstruct() {
		log.debug("[Macula] |- Plugin [Redis Lock Plugin] Auto Configure.");
	}

	@Bean
	public RedisLockAop redisAop(RedissonClient redissonClient) {
		log.debug("[Macula] |- Bean [RedisLockAop] Auto Configure.");
		return new RedisLockAop(redissonClient);
	}

}
