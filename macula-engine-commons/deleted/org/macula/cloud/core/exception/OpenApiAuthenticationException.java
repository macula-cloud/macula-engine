package org.macula.cloud.core.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.macula.cloud.api.context.CloudApplicationContext;
import org.macula.cloud.api.exception.MaculaException;
import org.macula.cloud.core.constant.GlobalConstant;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * <p>
 * <b>OpenApiAuthenticationException</b> OpenAPI认证异常
 * </p>
 * 
 */
public class OpenApiAuthenticationException extends MaculaException {

	private static final long serialVersionUID = Versions.serialVersion;

	public OpenApiAuthenticationException(HttpServletRequest request, HttpServletResponse response, String message) {
		super(message);
		setRequest(request, response);
	}

	public OpenApiAuthenticationException(HttpServletRequest request, HttpServletResponse response, String message, Throwable ex) {
		super(message, ex);
		setRequest(request, response);
	}

	@Override
	public String getParentCode() {
		return GlobalConstant.MACULA_CLOUD_PARAM_CODE;
	}

	private void setRequest(HttpServletRequest request, HttpServletResponse response) {
		// 由于出现异常后，系统并没有进入DispatchServlet，所以RequestContextHolder中并没有Request，所以需要自己设置
		// 以便getMessage时可以得到Locale
		RequestAttributes previousRequestAttributes = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes requestAttributes = null;
		if (previousRequestAttributes == null || previousRequestAttributes.getClass().equals(ServletRequestAttributes.class)) {
			requestAttributes = new ServletRequestAttributes(request);
			RequestContextHolder.setRequestAttributes(requestAttributes, false);
		}

		// 处理请求中的locale参数
		String newLocale = request.getParameter(LocaleChangeInterceptor.DEFAULT_PARAM_NAME);
		if (newLocale != null) {
			LocaleResolver localeResolver = CloudApplicationContext.getContainer().getBean(DispatcherServlet.LOCALE_RESOLVER_BEAN_NAME,
					LocaleResolver.class);
			if (localeResolver != null) {
				request.setAttribute(DispatcherServlet.LOCALE_RESOLVER_ATTRIBUTE, localeResolver);
				localeResolver.setLocale(request, response, StringUtils.parseLocaleString(newLocale));
			}
		}
	}

}
