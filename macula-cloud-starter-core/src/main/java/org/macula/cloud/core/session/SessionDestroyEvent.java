package org.macula.cloud.core.session;


import org.macula.cloud.core.event.MaculaEvent;

public class SessionDestroyEvent extends MaculaEvent<Session> {

	private static final long serialVersionUID = 1L;

	public SessionDestroyEvent(Session session) {
		super(session);
	}

}
