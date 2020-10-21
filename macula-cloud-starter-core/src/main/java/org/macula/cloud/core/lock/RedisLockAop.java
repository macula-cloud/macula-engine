package org.macula.cloud.core.lock;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.macula.cloud.core.annotation.RedisLock;
import org.macula.cloud.core.annotation.RedisLock.LockFailedPolicy;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
public class RedisLockAop extends AbstractLockAop {

	private RedissonClient redissonClient;

	@Around("@annotation(redisLock)")
	public Object execute(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
		String lockKey = redisLock.value();
		Method method = getMethod(joinPoint);
		String key = redisLock.prefix() + ":" + parseKey(lockKey, method, joinPoint.getArgs());
		int retryTimes = redisLock.action().equals(LockFailedPolicy.CONTINUE) ? redisLock.retryTimes() : 0;
		RLock rlock = redissonClient.getLock(key);
		boolean lock = rlock.tryLock(redisLock.expireTime(), TimeUnit.MILLISECONDS);
		while (!lock && retryTimes-- > 0) {
			Thread.sleep(redisLock.sleepMills());
			lock = rlock.tryLock(redisLock.expireTime(), TimeUnit.MILLISECONDS);
		}
		if (lock) {
			// 得到锁,执行方法，释放锁
			if (log.isDebugEnabled()) {
				log.debug("get lock success : " + key);
			}
			try {
				return joinPoint.proceed();
			} finally {
				rlock.unlock();
				if (log.isDebugEnabled()) {
					log.debug("release redis lock : " + key + " success");
				}
			}
		}
		return null;
	}
}
