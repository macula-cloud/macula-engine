package org.macula.cloud.sdk.context;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

public class ServletApplicationContext {
	/**
	 * 获取当前HTTP请求的Request，只有在HTTP请求线程中调用生效
	 */
	public static HttpServletRequest getRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (null != attrs) {
			return attrs.getRequest();
		}
		return null;
	}

	/**
	 * 获取当前HTTP请求的SESSION，只在HTTP请求线程中生效
	 */
	public static HttpSession getSession() {
		HttpServletRequest req = getRequest();
		if (null != req) {
			return req.getSession(false);
		}
		return null;
	}

	/**
	 * 获取当前用户的Locale，需要在Servlet环境下使用
	 * 
	 * @return 返回当前用户的Locale,否则返回NULL
	 */
	public static Locale getCurrentUserLocale() {
		Locale locale = null;
		// 尝试获取用户的地区
		HttpServletRequest request = getRequest();
		if (null != request) {
			locale = RequestContextUtils.getLocale(request);
		}
		return locale;
	}
}
