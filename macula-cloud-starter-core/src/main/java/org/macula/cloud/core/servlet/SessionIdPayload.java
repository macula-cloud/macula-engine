package org.macula.cloud.core.servlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.macula.cloud.core.session.SessionId;

import java.util.Optional;

public class SessionIdPayload {

	public static Optional<String> extract(HttpServletRequest request) {
		String sessionId = request.getHeader(SessionId.AUTHORIZATION);
		if (sessionId == null) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (SessionId.AUTHORIZATION.equalsIgnoreCase(cookie.getName())) {
						sessionId = cookie.getValue();
					}
				}
			}
		}
		if (sessionId == null) {
			sessionId = request.getParameter(SessionId.AUTHORIZATION);
		}
		return Optional.ofNullable(sessionId);
	}

	public static void assemble(HttpServletRequest request, HttpServletResponse response, String token) {
		if (token == null) {
			return;
		}
		Optional<String> authorization = extract(request);
		if (!authorization.isPresent() || !token.equals(authorization.get())) {
			response.addHeader(SessionId.AUTHORIZATION, token);
			Cookie cookie = new Cookie(SessionId.AUTHORIZATION, token);
			cookie.setHttpOnly(true);
			response.addCookie(cookie);
		}
	}
}
