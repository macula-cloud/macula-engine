package org.macula.cloud.security.access;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.util.UriUtils;

public class RedirectLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

	private RequestCache requestCache;

	private final String REDIRECT_TO_PARAMETER = "redirect_to_url";

	public RedirectLoginUrlAuthenticationEntryPoint(String loginFormUrl, RequestCache requestCache) {
		super(loginFormUrl);
		this.requestCache = requestCache;
	}

	@Override
	protected String buildRedirectUrlToLoginPage(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) {
		String url = super.buildRedirectUrlToLoginPage(request, response, authException);
		StringBuilder urlBuilder = new StringBuilder(url);
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null) {
			String redirectToUrl = savedRequest.getRedirectUrl();
			redirectToUrl = UriUtils.encode(redirectToUrl, "UTF-8");
			urlBuilder.append(url.indexOf("?") > 0 ? "&" : "?").append(REDIRECT_TO_PARAMETER).append("=")
					.append(redirectToUrl);
		}

		return urlBuilder.toString();
	}

}
