//package org.macula.cloud.core.oauth2;
//
//import java.util.Map;
//
//import org.macula.cloud.core.principal.SubjectPrincipalConverter;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
//
//public class SubjectPrincipalExtractor implements PrincipalExtractor {
//
//	private final SubjectPrincipalConverter subjectPrincipalConverter;
//
//	public SubjectPrincipalExtractor(AuthoritiesExtractor authoritiesExtractor) {
//		subjectPrincipalConverter = new SubjectPrincipalConverter(authoritiesExtractor);
//	}
//
//	@Override
//	public Object extractPrincipal(Map<String, Object> map) {
//		return subjectPrincipalConverter.extractAuthentication(map).getDetails();
//	}
//}
