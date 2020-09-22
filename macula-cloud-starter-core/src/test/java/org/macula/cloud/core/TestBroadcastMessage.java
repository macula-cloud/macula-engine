package org.macula.cloud.core;

import org.macula.cloud.core.event.MaculaEvent;

public class TestBroadcastMessage extends MaculaEvent<String> {

	private static final long serialVersionUID = 1L;

	public TestBroadcastMessage(String source) {
		super(source);
	}

}
