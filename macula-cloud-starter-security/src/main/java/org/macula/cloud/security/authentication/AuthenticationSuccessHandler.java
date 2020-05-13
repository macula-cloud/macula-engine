package org.macula.cloud.security.authentication;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.macula.cloud.core.configure.CoreConfigurationProperties;
import org.macula.cloud.core.utils.HttpRequestUtils;
import org.macula.cloud.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private String defaultUrl;

	private CaptchaValidationPolicy captchaValidationPolicy;

	@Autowired(required = false)
	private AjaxAuthenticationSuccessHandler ajaxSuccessHandler;

	public AuthenticationSuccessHandler(CoreConfigurationProperties properties, CaptchaValidationPolicy captchaValidationPolicy) {
		this.defaultUrl = properties.getSecurity().getDefaultUrl();
		this.captchaValidationPolicy = captchaValidationPolicy;
		setTargetUrlParameter("redirect");
	}

	@PostConstruct
	private void init() {
		this.setDefaultTargetUrl(defaultUrl);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String username = authentication.getName();
		if (StringUtils.isNotEmpty(username)) {
			captchaValidationPolicy.clearCaptchaMarks(username);
		}

		if (authentication instanceof OAuth2Authentication) {
			OAuth2Authentication oauth2 = ((OAuth2Authentication) authentication);
			String tokenValue = ((OAuth2AuthenticationDetails) oauth2.getDetails()).getTokenValue();
			response.addCookie(new Cookie(HttpRequestUtils.OAUTH2_TOKEN_COOKIE, tokenValue));
		}

		//		if (authentication.getPrincipal() instanceof SubjectPrincipal) {
		//			String guid = ((SubjectPrincipal) authentication.getPrincipal()).getUserId();
		//			SubjectPrincipalCreatedEvent event = new SubjectPrincipalCreatedEvent(guid);
		//			CloudApplicationContext.getContainer().publishEvent(InstanceProcessEvent.wrap(event));
		//			Session session = new Session(request.getSession().getId(), guid);
		//			CloudApplicationContext.getContainer().publishEvent(new SessionCreatedEvent(session));
		//		}

		if (HttpRequestUtils.isAjaxRequest(request) && ajaxSuccessHandler != null) {
			ajaxSuccessHandler.onAuthenticationSuccess(request, response, authentication);
			return;
		}

		String targetUrl = HttpRequestUtils.getRedirectToValue(request);
		if (StringUtils.isNotEmpty(targetUrl)) {
			HttpRequestUtils.clearRedirectToValue(request);
			getRedirectStrategy().sendRedirect(request, response, targetUrl);
			return;
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
