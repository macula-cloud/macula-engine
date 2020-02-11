package org.macula.cloud.sdk.session;


import org.macula.cloud.sdk.event.MaculaEvent;

public class SessionCreatedEvent extends MaculaEvent<Session> {

	private static final long serialVersionUID = 1L;

	public SessionCreatedEvent(Session session) {
		super(session);
	}

}
