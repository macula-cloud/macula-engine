package org.macula.cloud.core.lock.annotation;


import java.lang.annotation.*;

import org.macula.cloud.core.lock.enums.LockFailedPolicy;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisLock {

	/**
	 * 对应key值
	 */
	String value() default "";

	String prefix() default "macula-lock";

	/**
	 * 到期时间
	 */
	int expireTime() default 20000;

	/**
	 * 当获取失败时候动作
	 */
	LockFailedPolicy action() default LockFailedPolicy.GIVEUP;

	/**
	 * 重试的间隔时间
	 */
	long sleepMills() default 200;

	/**
	 * 重试次数
	 */
	int retryTimes() default 5;
}
