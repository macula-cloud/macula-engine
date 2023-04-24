//package org.macula.cloud.core.servlet;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import org.macula.cloud.core.session.SessionId;
//import org.springframework.session.web.http.CookieHttpSessionIdResolver;
//import org.springframework.session.web.http.CookieSerializer;
//import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
//import org.springframework.session.web.http.HttpSessionIdResolver;
//
//public class CompositeHttpSessionIdResolver implements HttpSessionIdResolver {
//
//	private final List<HttpSessionIdResolver> sessionIdResolvers = new ArrayList<HttpSessionIdResolver>();
//
//	public CompositeHttpSessionIdResolver(CookieSerializer cookieSerializer) {
//		CookieHttpSessionIdResolver cookieResolver = new CookieHttpSessionIdResolver();
//		if (cookieSerializer != null) {
//			cookieResolver.setCookieSerializer(cookieSerializer);
//		}
//		HeaderHttpSessionIdResolver headerResolver = new HeaderHttpSessionIdResolver(SessionId.AUTHORIZATION);
//		sessionIdResolvers.add(cookieResolver);
//		sessionIdResolvers.add(headerResolver);
//	}
//
//	@Override
//	public List<String> resolveSessionIds(HttpServletRequest request) {
//		Set<String> sessionIds = new HashSet<String>();
//		sessionIdResolvers.forEach(resolver -> sessionIds.addAll(resolver.resolveSessionIds(request)));
//		return new ArrayList<String>(sessionIds);
//	}
//
//	@Override
//	public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
//		sessionIdResolvers.forEach(resolver -> resolver.setSessionId(request, response, sessionId));
//	}
//
//	@Override
//	public void expireSession(HttpServletRequest request, HttpServletResponse response) {
//		sessionIdResolvers.forEach(resolver -> resolver.expireSession(request, response));
//	}
//
//	public void addSessionIdResolver(int index, HttpSessionIdResolver resolver) {
//		this.sessionIdResolvers.add(index, resolver);
//	}
//}
