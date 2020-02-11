package org.macula.cloud.sdk.session;

import org.macula.cloud.sdk.principal.SubjectPrincipalSessionStorage;
import org.springframework.context.ApplicationListener;

public class SessionDestroyListener implements ApplicationListener<SessionCreatedEvent> {

	private SubjectPrincipalSessionStorage sessionStorage;

	public SessionDestroyListener(SubjectPrincipalSessionStorage sessionStorage) {
		this.sessionStorage = sessionStorage;
	}

	@Override
	public void onApplicationEvent(SessionCreatedEvent event) {
		Session session = event.getSource();
		sessionStorage.destory(session);
	}

}
