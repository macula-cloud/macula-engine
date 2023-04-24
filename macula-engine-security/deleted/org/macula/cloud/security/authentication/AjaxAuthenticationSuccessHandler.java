package org.macula.cloud.security.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

public interface AjaxAuthenticationSuccessHandler {

	void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication);

}
