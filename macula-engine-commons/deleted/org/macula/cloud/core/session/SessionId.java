package org.macula.cloud.core.session;

import java.util.UUID;

public class SessionId {

	public static final String AUTHORIZATION = "SESSION";

	public static String createNewSessionId() {
		return UUID.randomUUID().toString();
	}
}
