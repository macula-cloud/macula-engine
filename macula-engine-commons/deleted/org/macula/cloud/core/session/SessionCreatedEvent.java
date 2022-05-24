package org.macula.cloud.core.session;

import org.macula.cloud.core.event.MaculaEvent;

public class SessionCreatedEvent extends MaculaEvent<Session> {

	private static final long serialVersionUID = Versions.serialVersion;

	public SessionCreatedEvent(Session session) {
		super(session);
	}

}
