
package org.macula.cloud.api.context;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

/**
 * <p>
 * <b>CloudApplicationContext</b> API包的Spring上下文
 * </p>
 */
public class CloudApplicationContext {

	/** Spring container */
	protected static ApplicationContext container;

	/**
	 * 获取bean
	 * 
	 * @param name
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		try {
			return (T) getContainer().getBean(name);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取bean
	 * 
	 * @param name bean name
	 * @param clz  bean class
	 * @return Object
	 */
	public static <T> T getBean(String name, Class<T> clz) {
		return getContainer().getBean(name, clz);
	}

	/**
	 * 获取bean
	 * 
	 * @param clz bean class
	 * @return Object
	 */
	public static <T> T getBean(Class<T> clz) {
		return getContainer().getBean(clz);
	}

	/**
	 * 获取i18n字符串，如果不存在则原样返回，Locale是采用用户信息中的， 如果不存在，则使用系统默认
	 * 
	 * @param code i18n的编码
	 * @return i18n字符串
	 */
	public static String getMessage(String code) {
		return getMessage(code, (Object[]) null);
	}

	/**
	 * 获取i18n字符串，如果不存在则原样返回，Locale是由参数指定.
	 * 
	 * @param code   i18n的编码
	 * @param locale 指定的地区信息
	 * @return i18n字符串
	 */
	public static String getMessage(String code, Locale locale) {
		return getMessage(code, null, locale);
	}

	/**
	 * 获取i18n字符串，如果不存在则原样返回，Locale是采用用户信息中的， 如果不存在，则使用系统默认
	 * 
	 * @param code i18n的编码
	 * @param args 参数值
	 * @return i18n字符串
	 */
	public static String getMessage(String code, Object[] args) {
		// Locale locale = getCurrentUserLocale();
		Locale locale = null;
		if (null == locale) {
			// 获取操作系统默认的地区
			locale = Locale.getDefault();
		}
		return getMessage(code, args, code, locale);
	}

	/**
	 * 获取i18n字符串，如果不存在则原样返回，Locale是由参数指定.
	 * 
	 * @param code   i18n的编码
	 * @param args   参数值
	 * @param locale 指定的地区信息
	 * @return i18n字符串
	 */
	public static String getMessage(String code, Object[] args, Locale locale) {
		return getMessage(code, args, code, locale);
	}

	/**
	 * 获取i18n字符串
	 * 
	 * @param code           i18n的编码
	 * @param args           参数值
	 * @param defaultMessage 如果找不到code对应的i18n信息，则使用该默认信息
	 * @param locale         地区编码
	 * @return i18n字符串
	 */
	public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		if (getContainer() == null) {
			return code;
		}
		try {
			return getContainer().getMessage(code, args, defaultMessage, locale);
		} catch (NoSuchMessageException ex) {
			return code;
		}
	}

	/**
	 * 获取i18n字符串
	 * 
	 * @param messagesourceresolvable
	 * @param locale                  地区编码
	 * @return i18n字符串
	 */
	public static String getMessage(MessageSourceResolvable messagesourceresolvable, Locale locale) {
		if (getContainer() == null) {
			return messagesourceresolvable.getDefaultMessage();
		}
		return getContainer().getMessage(messagesourceresolvable, locale);
	}

	public static String getMessage(MessageSourceResolvable messagesourceresolvable) {
		// Locale locale = getCurrentUserLocale();
		Locale locale = null;
		if (null == locale) {
			// 获取操作系统默认的地区
			locale = Locale.getDefault();
		}
		return getMessage(messagesourceresolvable, locale);
	}

	public static synchronized ApplicationContext getContainer() {
		return container;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		container = applicationContext;
	}
}
