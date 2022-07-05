//package org.macula.cloud.core.session;
//
//import org.macula.cloud.core.principal.SubjectPrincipalSessionStorage;
//import org.springframework.context.ApplicationListener;
//
//public class SessionDestroyListener implements ApplicationListener<SessionCreatedEvent> {
//
//	private SubjectPrincipalSessionStorage sessionStorage;
//
//	public SessionDestroyListener(SubjectPrincipalSessionStorage sessionStorage) {
//		this.sessionStorage = sessionStorage;
//	}
//
//	@Override
//	public void onApplicationEvent(SessionCreatedEvent event) {
//		Session session = event.getSource();
//		sessionStorage.destory(session);
//	}
//
//}
