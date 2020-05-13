package org.macula.cloud.core.event;

import org.macula.cloud.core.domain.AccessLog;

public class AccessLogEvent extends MaculaEvent<AccessLog> {

	private static final long serialVersionUID = 1L;

	public AccessLogEvent(AccessLog source) {
		super(source);
	}

}
