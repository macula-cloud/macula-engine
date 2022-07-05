package org.macula.engine.security.session;

import org.macula.engine.assistant.constants.Versions;

import org.springframework.context.ApplicationEvent;

public class SessionCreatedEvent extends ApplicationEvent {

	private static final long serialVersionUID = Versions.serialVersion;

	public SessionCreatedEvent(Session session) {
		super(session);
	}

	@Override
	public Session getSource() {
		return (Session) super.getSource();
	}

}
