package org.macula.cloud.core.oauth2;

import org.macula.cloud.core.principal.SubjectPrincipal;
import org.macula.cloud.core.utils.SecurityUtils;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.jwt.crypto.sign.SignerVerifier;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

public class SubjectPrincipalUserInfoTokenServices extends UserInfoTokenServices {

	private SignerVerifier signer;
	private String clientId;

	public SubjectPrincipalUserInfoTokenServices(String userInfoEndpointUrl, String clientId, PrincipalExtractor principalExtractor,
			AuthoritiesExtractor authoritiesExtractor, SignerVerifier signer) {
		super(userInfoEndpointUrl, clientId);
		if (principalExtractor != null) {
			super.setPrincipalExtractor(principalExtractor);
		}
		if (authoritiesExtractor != null) {
			super.setAuthoritiesExtractor(authoritiesExtractor);
		}
		this.clientId = clientId;
		this.signer = signer;
	}

	@Override
	@Cacheable(cacheNames = OAuth2AccessToken.ACCESS_TOKEN, key = "#accessToken", unless = "#result == null")
	public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
		if (accessToken.indexOf(".") > 0) {
			// JWT token
			SubjectPrincipal principal = SecurityUtils.convertPrincipal(accessToken, signer);
			if (principal != null) {
				OAuth2Request request = new OAuth2Request(null, this.clientId, null, true, null, null, null, null, null);
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, "N/A", principal.getAuthorities());
				return new OAuth2Authentication(request, token);
			}
		}
		return super.loadAuthentication(accessToken);
	}

}
