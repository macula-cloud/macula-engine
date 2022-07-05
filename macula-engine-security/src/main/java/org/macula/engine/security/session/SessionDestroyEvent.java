package org.macula.engine.security.session;

import org.macula.engine.assistant.constants.Versions;

import org.springframework.context.ApplicationEvent;

public class SessionDestroyEvent extends ApplicationEvent {

	private static final long serialVersionUID = Versions.serialVersion;

	public SessionDestroyEvent(Session session) {
		super(session);
	}

}
