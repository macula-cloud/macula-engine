package org.macula.cloud.sdk.utils;

import java.util.HashSet;
import java.util.Set;

import org.macula.cloud.sdk.configure.model.SDKSecurityProperties;
import org.macula.cloud.sdk.principal.SubjectAuthentication;
import org.macula.cloud.sdk.principal.SubjectPrincipal;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.StringUtils;

public final class SecurityUtils {

	private static AnonymousAuthenticationToken anonymousToken = new AnonymousAuthenticationToken("key", "anonymous",
			AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));

	private static SubjectPrincipal anonymousSubject = new SubjectPrincipal(anonymousToken.getName());
	static {
		anonymousSubject.addAuthority("ROLE_ANONYMOUS");
	}

	public static Authentication cast(SubjectPrincipal principal) {
		return new SubjectAuthentication(principal);
	}

	public static Authentication getAnonymous() {
		return anonymousToken;
	}

	public static SubjectPrincipal getSubjectPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			if (authentication.getPrincipal() instanceof SubjectPrincipal) {
				return (SubjectPrincipal) authentication.getPrincipal();
			}
			Object details = authentication.getDetails();
			if (details instanceof OAuth2AuthenticationDetails) {
				Object decodedDetails = ((OAuth2AuthenticationDetails) details).getDecodedDetails();
				if (decodedDetails instanceof SubjectPrincipal) {
					return (SubjectPrincipal) decodedDetails;
				}
			}
		}
		return anonymousSubject;
	}

	public static String[] getResourceAuthorities(SDKSecurityProperties securityProperties, String springAppName) {
		Set<String> hasAuthories = new HashSet<String>();
		String authories = securityProperties.getNeedAuthories();
		if (StringUtils.hasLength(authories)) {
			hasAuthories.addAll(StringUtils.commaDelimitedListToSet(authories));
		}
		if (securityProperties.isIncludeDefault()) {
			hasAuthories.add("macula-cloud-gateway"); // for user
			if (StringUtils.hasLength(springAppName)) {
				hasAuthories.add(springAppName); // for oauth2 client
			}
		}

		hasAuthories.forEach(t -> t.toLowerCase());
		return StringUtils.toStringArray(hasAuthories);
	}
}
