package org.macula.cloud.security.authentication;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.macula.cloud.sdk.principal.SubjectPrincipal;
import org.macula.cloud.sdk.principal.SubjectPrincipalSessionStorage;
import org.macula.cloud.sdk.principal.SubjectType;
import org.macula.cloud.sdk.utils.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

public class CasPrincipalAuthenticationConverter extends DefaultUserAuthenticationConverter {

	private final SubjectPrincipalSessionStorage principalStorage;

	public CasPrincipalAuthenticationConverter(SubjectPrincipalSessionStorage principalStorage) {
		this.principalStorage = principalStorage;
	}

	@Override
	public Authentication extractAuthentication(Map<String, ?> map) {
		Authentication authentication = super.extractAuthentication(map);
		if (authentication == null) {
			authentication = loadCasIntrospectionAuthentication(map);
		}
		return authentication;
	}

	/**
	 * load from CAS introspect, see
	 * see @org.apereo.cas.support.oauth.web.response.introspection.OAuth20IntrospectionAccessTokenResponse
	 * 
	 * @param map
	 * @return
	 */
	protected Authentication loadCasIntrospectionAuthentication(Map<String, ?> map) {
		String id = (String) map.get("uniqueSecurityName");
		String grantType = (String) map.get(OAuth2Utils.GRANT_TYPE);
		if (grantType == null) {
			grantType = (String) map.get("grantType");
		}
		SubjectType subjectType = StringUtils.equalsIgnoreCase("client_credentials", grantType) ? SubjectType.CLIENT
				: SubjectType.CLIENT;
		if (subjectType == SubjectType.USER) {
			SubjectPrincipal principal = principalStorage.checkoutPrincipal(id);
			if (principal != null) {
				Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
				principal.getAuthorities().forEach(t -> authorities.add(new SimpleGrantedAuthority(t)));
				authorities.add(new SimpleGrantedAuthority("macula-cloud-gateway"));
				return SecurityUtils.cast(principal);
			}
		} else {
			SubjectPrincipal principal = principalStorage.checkoutPrincipal(id);
			if (principal == null) {
				principal = new SubjectPrincipal(id, subjectType);
				// TODO add authorities from map or application
				principalStorage.commit(principal);
			}
			return SecurityUtils.cast(principal);
		}
		return null;
	}
}
