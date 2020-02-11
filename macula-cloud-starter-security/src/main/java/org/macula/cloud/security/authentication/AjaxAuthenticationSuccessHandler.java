package org.macula.cloud.security.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

public interface AjaxAuthenticationSuccessHandler {

	void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication);

}
