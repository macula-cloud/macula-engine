//package org.macula.cloud.core.principal;
//
//import java.io.Serializable;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.commons.collections4.CollectionUtils;
//import org.macula.cloud.core.annotation.RedisLock;
//import org.springframework.context.ApplicationListener;
//import org.springframework.core.Ordered;
//
//public class SubjectPrincipalCreatedListener implements ApplicationListener<SubjectPrincipalCreatedEvent> {
//
//	private final SubjectPrincipalSessionStorage sessionStorage;
//	private final List<SubjectPropertyResolver> propertyResolvers;
//
//	public SubjectPrincipalCreatedListener(SubjectPrincipalSessionStorage sessionStorage, List<SubjectPropertyResolver> propertyResolvers) {
//		this.sessionStorage = sessionStorage;
//		this.propertyResolvers = propertyResolvers;
//		this.propertyResolvers.sort(Comparator.comparingInt(Ordered::getOrder));
//	}
//
//	@Override
//	@RedisLock(prefix = "SubjectPrincipalCreatedListener", value = "#event.guid")
//	public void onApplicationEvent(SubjectPrincipalCreatedEvent event) {
//		String guid = event.getSource();
//		SubjectPrincipal subjectPrincipal = sessionStorage.checkoutPrincipal(guid);
//		if (subjectPrincipal != null && CollectionUtils.isNotEmpty(propertyResolvers)) {
//			for (SubjectPropertyResolver userPropertyResolver : propertyResolvers) {
//				Map<String, Serializable> properties = userPropertyResolver.getCustomProperties(subjectPrincipal);
//				if (properties != null) {
//					subjectPrincipal.addAdditionInfo(properties);
//				}
//				Set<String> authories = userPropertyResolver.getCustomAuthories(subjectPrincipal);
//				if (authories != null) {
//					subjectPrincipal.addAuthorities(authories);
//				}
//			}
//			sessionStorage.commit(subjectPrincipal);
//		}
//	}
//
//}
