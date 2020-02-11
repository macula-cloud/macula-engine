package org.macula.cloud.sdk.lock.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.macula.cloud.sdk.lock.annotation.RedisLock;
import org.macula.cloud.sdk.lock.distributedlock.DistributedLock;
import org.macula.cloud.sdk.lock.enums.LockFailedPolicy;
import org.springframework.lang.NonNull;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedisLockAop extends AbstractLockAop {

	private DistributedLock distributedLock;

	public RedisLockAop(@NonNull DistributedLock distributedLock) {
		this.distributedLock = distributedLock;
	}

	@Around("@annotation(redisLock)")
	public void execute(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
		String lockKey = redisLock.value();
		Method method = getMethod(joinPoint);
		String key = redisLock.prefix() + ":" + parseKey(lockKey, method, joinPoint.getArgs());
		int retryTimes = redisLock.action().equals(LockFailedPolicy.CONTINUE) ? redisLock.retryTimes() : 0;

		boolean lock = distributedLock.lock(key, redisLock.expireTime(), retryTimes, redisLock.sleepMills());
		if (!lock) {
			log.debug("get lock failed : " + key);
			return;
		}
		// 得到锁,执行方法，释放锁
		log.debug("get lock success : " + key);
		try {
			joinPoint.proceed();
		}
		catch (Exception e) {
			log.error("execute redis locked method occured an exception", e);
		}
		finally {
			boolean releaseResult = distributedLock.releaseLock(key);
			log.debug("release redis lock : " + key + (releaseResult ? " success" : " failed"));
		}
	}
}
