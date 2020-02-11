package org.macula.cloud.sdk.session;


import org.macula.cloud.sdk.event.MaculaEvent;

public class SessionDestroyEvent extends MaculaEvent<Session> {

	private static final long serialVersionUID = 1L;

	public SessionDestroyEvent(Session session) {
		super(session);
	}

}
