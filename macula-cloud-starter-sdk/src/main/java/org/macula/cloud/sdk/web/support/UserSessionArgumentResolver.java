package org.macula.cloud.sdk.web.support;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.macula.cloud.sdk.principal.SubjectPrincipalSessionStorage;
import org.macula.cloud.sdk.session.Session;
import org.springframework.core.MethodParameter;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserSessionArgumentResolver implements HandlerMethodArgumentResolver {

	private final HttpSessionIdResolver sessionIdResolver;
	private final SubjectPrincipalSessionStorage sessionStorage;

	public UserSessionArgumentResolver(SubjectPrincipalSessionStorage sessionStorage,
			HttpSessionIdResolver sessionIdResolver) {
		this.sessionIdResolver = sessionIdResolver;
		this.sessionStorage = sessionStorage;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return Session.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Session resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		List<String> sessionIds = sessionIdResolver
				.resolveSessionIds((HttpServletRequest) webRequest.getNativeRequest());
		for (String sessionId : sessionIds) {
			Session userSession = sessionStorage.checkoutSession(sessionId);
			if (userSession != null) {
				return userSession;
			}
		}
		return null;
	}

}
