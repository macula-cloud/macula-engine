package org.macula.cloud.core.event;

import org.macula.engine.core.domain.AccessLog;

public class AccessLogEvent extends MaculaEvent<AccessLog> {

	private static final long serialVersionUID = Versions.serialVersion;

	public AccessLogEvent(AccessLog source) {
		super(source);
	}

}
