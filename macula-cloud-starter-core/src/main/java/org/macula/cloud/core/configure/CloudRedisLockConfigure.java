package org.macula.cloud.core.configure;

import org.macula.cloud.core.lock.aop.RedisLockAop;
import org.macula.cloud.core.lock.distributedlock.RedisDistributedLock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 分布式锁自动配置器
 */
@Configuration
@ConditionalOnBean(name = "redisTemplate")
@ConditionalOnProperty(prefix = "macula.cloud", name = "redis.lock", havingValue = "true", matchIfMissing = false)
public class CloudRedisLockConfigure {

	@Bean
	public RedisDistributedLock redisDistributedLock(@Qualifier("redisTemplate") RedisTemplate<?, ?> redisObjectTemplate) {
		return new RedisDistributedLock(redisObjectTemplate);
	}

	@Bean
	public RedisLockAop redisAop(RedisDistributedLock redisDistributedLock) {
		return new RedisLockAop(redisDistributedLock);
	}

}
