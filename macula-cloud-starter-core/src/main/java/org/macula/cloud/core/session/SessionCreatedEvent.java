package org.macula.cloud.core.session;


import org.macula.cloud.core.event.MaculaEvent;

public class SessionCreatedEvent extends MaculaEvent<Session> {

	private static final long serialVersionUID = 1L;

	public SessionCreatedEvent(Session session) {
		super(session);
	}

}
