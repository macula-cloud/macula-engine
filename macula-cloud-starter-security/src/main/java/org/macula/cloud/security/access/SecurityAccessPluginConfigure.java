package org.macula.cloud.security.access;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public interface SecurityAccessPluginConfigure {

	default void init(AuthenticationSuccessHandler authenticationSuccessHandler,
			AuthenticationFailureHandler authenticationFailureHandler, LogoutSuccessHandler logoutSuccessHandler) {
	}

	default void configureAccess(HttpSecurity http) throws Exception {
	}

	default void configureResourceServer(HttpSecurity http) throws Exception {
	}

}
