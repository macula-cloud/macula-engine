package org.macula.cloud.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisLock {

	/**
	 * 对应key值
	 */
	String value() default "";

	/** 
	 * 前缀 
	 */
	String prefix() default "lock";

	/**
	 * 到期时间
	 */
	long expireTime() default 20000;

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

	/** 失败策略 */
	public enum LockFailedPolicy {

		/**
		 * 放弃
		 */
		GIVEUP,
		/**
		 * 继续
		 */
		CONTINUE
	}

}
