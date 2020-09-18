package org.macula.cloud.core.lock.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.macula.cloud.core.lock.annotation.ZookeeperLock;
import org.macula.cloud.core.lock.distributedlock.DistributedLock;
import org.macula.cloud.core.lock.enums.LockFailedPolicy;
import org.springframework.lang.NonNull;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
public class ZookeeperLockAop extends AbstractLockAop {

	private DistributedLock distributedLock;

	public ZookeeperLockAop(@NonNull DistributedLock distributedLock) {
		this.distributedLock = distributedLock;
	}

	@Around("@annotation(zookeeperLock)")
	public Object execute(ProceedingJoinPoint joinPoint, ZookeeperLock zookeeperLock) throws Throwable {
		String lockKey = zookeeperLock.value();
		Method method = getMethod(joinPoint);
		String key = zookeeperLock.prefix() + "/" + parseKey(lockKey, method, joinPoint.getArgs());
		int retryTimes = zookeeperLock.action().equals(LockFailedPolicy.CONTINUE) ? zookeeperLock.retryTimes() : 0;

		boolean lock = distributedLock.lock(key, zookeeperLock.expireMills(), retryTimes, zookeeperLock.sleepMills());
		if (lock) {
			// 得到锁,执行方法，释放锁
			log.debug("get lock success : " + key);
			try {
				return joinPoint.proceed();
			} catch (Exception e) {
				log.error("execute zookeeper locked method occured an exception", e);
			} finally {
				boolean releaseResult = distributedLock.releaseLock(key);
				log.debug("release zookeeper lock : " + key + (releaseResult ? " success" : " failed"));
			}
		}
		throw new RuntimeException(String.format("Error get zookeeper distributed lock %s", zookeeperLock.value()));
	}
}
