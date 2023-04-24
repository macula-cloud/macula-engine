//package org.macula.cloud.core.servlet;
//
//import java.io.IOException;
//import java.util.List;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import org.macula.cloud.core.principal.SubjectPrincipal;
//import org.macula.cloud.core.principal.SubjectPrincipalSessionStorage;
//import org.macula.cloud.core.session.Session;
//import org.macula.cloud.core.utils.SecurityUtils;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.session.web.http.HttpSessionIdResolver;
//
//public class PrincipalResolverLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {
//
//	private final HttpSessionIdResolver sessionIdResolver;
//	private final SubjectPrincipalSessionStorage sessionStorage;
//
//	private static final String AUTHENTICATION_ATTR = PrincipalResolverLoginProcessingFilter.class.getName();
//
//	public PrincipalResolverLoginProcessingFilter(SubjectPrincipalSessionStorage sessionStorage,
//			HttpSessionIdResolver sessionIdResolver) {
//		super("/**");
//		this.sessionIdResolver = sessionIdResolver;
//		this.sessionStorage = sessionStorage;
//	}
//
//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//			throws AuthenticationException, IOException, ServletException {
//		return (Authentication) request.getAttribute(AUTHENTICATION_ATTR);
//	}
//
//	@Override
//	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
//		if (super.requiresAuthentication(request, response)) {
//			SecurityContext context = SecurityContextHolder.getContext();
//			if (context.getAuthentication() == null || !context.getAuthentication().isAuthenticated()) {
//				Authentication authentication = resolveAuthentication(request, response);
//				if (authentication != null) {
//					request.setAttribute(AUTHENTICATION_ATTR, authentication);
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//
//	protected Authentication resolveAuthentication(HttpServletRequest request, HttpServletResponse response) {
//		List<String> sessionIds = sessionIdResolver.resolveSessionIds(request);
//		for (String sessionId : sessionIds) {
//			Session userSession = sessionStorage.checkoutSession(sessionId);
//			if (userSession != null) {
//				SubjectPrincipal subjectPrincipal = sessionStorage.checkoutPrincipal(userSession.getGuid());
//				if (subjectPrincipal != null) {
//					return SecurityUtils.cast(subjectPrincipal);
//				}
//			}
//		}
//		return null;
//	}
//
//	@Override
//	public void afterPropertiesSet() {
//	}
//}
