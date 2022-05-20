package org.macula.engine.web.enums;

/**
 * <p>Description: 数据访问策略枚举类型 </p>
 */
public enum DataAccessStrategy {

	/**
	 * 本地访问，利用service直连数据库
	 */
	LOCAL,

	/**
	 * 远程访问，利用Feign等远程访问数据
	 */
	REMOTE;
}
