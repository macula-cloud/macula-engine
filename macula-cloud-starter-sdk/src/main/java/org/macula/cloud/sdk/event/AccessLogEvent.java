package org.macula.cloud.sdk.event;

import org.macula.cloud.sdk.domain.AccessLog;

public class AccessLogEvent extends MaculaEvent<AccessLog> {

	private static final long serialVersionUID = 1L;

	public AccessLogEvent(AccessLog source) {
		super(source);
	}

}
