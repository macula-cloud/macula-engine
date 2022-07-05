package org.macula.engine.security.session;

import cn.hutool.core.util.IdUtil;

public class SessionId {

	public static final String AUTHORIZATION = "SESSION";

	public static String createNewSessionId() {
		return IdUtil.getSnowflakeNextIdStr();
	}
}
