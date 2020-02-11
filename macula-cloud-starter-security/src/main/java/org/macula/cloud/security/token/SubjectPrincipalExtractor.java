package org.macula.cloud.security.token;

import java.util.Map;

import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedPrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;

public class SubjectPrincipalExtractor implements PrincipalExtractor {

	private FixedPrincipalExtractor fixedPrincipalExtractor = new FixedPrincipalExtractor();

	@Override
	public Object extractPrincipal(Map<String, Object> map) {
//		Map<String, Object> authentication = (Map<String, Object>) map.get("userAuthentication");
//		if (authentication != null) {
//			Object principal = authentication.get("principal");
//			User user = new User();
//			try {
//				BeanUtils.populate(user, (Map<String, Object>) principal);
//			} catch (Exception e) {
//				throw new InvalidTokenException("");
//			}
//			return user;
//		} else {
		Object principal = this.fixedPrincipalExtractor.extractPrincipal(map);
		return (principal == null ? "unknown" : principal);
//		}
	}
}
