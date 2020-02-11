package org.macula.cloud.sdk.event;

import org.macula.cloud.sdk.domain.GatewayLogRecord;

public class GatewayLogRecordEvent extends MaculaEvent<GatewayLogRecord> {

	private static final long serialVersionUID = 1L;

	public GatewayLogRecordEvent(GatewayLogRecord source) {
		super(source);
	}

}
