package org.macula.cloud.core.principal;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

public class SubjectPrincipalConverter extends DefaultAccessTokenConverter {

	private final UserDetailsService userDetailsService;
	private final ClientDetailsService clientDetailsService;

	public SubjectPrincipalConverter() {
		this(null, null);
	}

	public SubjectPrincipalConverter(UserDetailsService userDetailsService, ClientDetailsService clientDetailsService) {
		this.userDetailsService = userDetailsService;
		this.clientDetailsService = clientDetailsService;
	}

	@Override
	public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) super.convertAccessToken(token, authentication);
		Object details = authentication.getPrincipal();
		if (details instanceof SubjectPrincipal) {
			SubjectPrincipal user = (SubjectPrincipal) userDetailsService.loadUserByUsername(((SubjectPrincipal) details).getUsername());
			map.put("userId", user.getUserId());
			map.put("language", user.getLanguage());
			map.put("timeZone", user.getTimeZone());
			map.put("email", user.getEmail());
			map.put("mobile", user.getMobile());
			map.put("organizationId", user.getOrganizationId());
			if (user.getAdditionInfo() != null) {
				map.put("additionInfo", user.getAdditionInfo());
			}
		} else if (details instanceof String) {
			ClientDetails client = clientDetailsService.loadClientByClientId((String) details);
			if (client.getAdditionalInformation() != null) {
				map.put("additionInfo", client.getAdditionalInformation());
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
		if (map.get("principal") != null) {
			map = (Map<String, ?>) map.get("principal");
		}
		if (!map.containsKey("user_name")) {
			((Map<String, Object>) map).put("user_name", ((Map<String, Object>) map).get("username"));
		}
		OAuth2Authentication authentication = super.extractAuthentication(map);
		SubjectPrincipal principal = new SubjectPrincipal(authentication.getName(), "[unknown password]", authentication.getAuthorities());
		principal.setUserId((String) map.getOrDefault("userId", null));
		principal.setOrganizationId((String) map.getOrDefault("organizationId", null));
		principal.setLanguage((String) map.getOrDefault("language", null));
		principal.setTimeZone((String) map.getOrDefault("timeZone", null));
		principal.setOrganizationId((String) map.getOrDefault("organizationId", null));
		principal.setEmail((String) map.getOrDefault("email", null));
		principal.setMobile((String) map.getOrDefault("mobile", null));
		if (map.containsKey("clientId")) {
			principal.setClientId((String) map.getOrDefault("clientId", null));
			principal.setClientAccessTokenValiditySeconds((Integer) map.get("clientAccessTokenValiditySeconds"));
			principal.setClientRefreshTokenValiditySeconds((Integer) map.get("clientRefreshTokenValiditySeconds"));
			principal.setClientAuthorizedGrantTypes((Collection<String>) map.get("clientAuthorizedGrantTypes"));
			principal.setClientAutoApproveScopes((Collection<String>) map.get("clientAutoApproveScopes"));
			principal.setClientRegisteredRedirectUri((Collection<String>) map.get("clientRegisteredRedirectUri"));
			principal.setClientResourceIds((Collection<String>) map.get("clientResourceIds"));
			principal.setClientScope((Collection<String>) map.get("clientScope"));
		}
		if (map.get("additionInfo") != null) {
			principal.setAdditionInfo((Map<String, Serializable>) map.get("additionInfo"));
		}
		authentication.setDetails(principal);
		return authentication;
	}

}
