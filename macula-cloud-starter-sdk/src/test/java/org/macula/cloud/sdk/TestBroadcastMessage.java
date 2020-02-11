package org.macula.cloud.sdk;


import org.macula.cloud.sdk.event.MaculaEvent;

public class TestBroadcastMessage extends MaculaEvent<String> {

	private static final long serialVersionUID = 1L;

	public TestBroadcastMessage(String source) {
		super(source);
	}

}
