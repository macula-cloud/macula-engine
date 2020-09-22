package org.macula.cloud.core.configure;

import org.macula.cloud.core.lock.RedisLockAop;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式锁自动配置器
 */
@Configuration
@ConditionalOnProperty(prefix = "macula.cloud", name = "redis.lock", havingValue = "true", matchIfMissing = true)
public class CloudRedisLockConfigure {

	@Bean
	public RedisLockAop redisAop(RedissonClient redissonClient) {
		return new RedisLockAop(redissonClient);
	}

}
