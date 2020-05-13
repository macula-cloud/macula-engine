package org.macula.cloud.core.event;

import org.macula.cloud.core.domain.GatewayLogRecord;

public class GatewayLogRecordEvent extends MaculaEvent<GatewayLogRecord> {

	private static final long serialVersionUID = 1L;

	public GatewayLogRecordEvent(GatewayLogRecord source) {
		super(source);
	}

}
