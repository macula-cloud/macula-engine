package org.macula.cloud.core.oauth2;

import java.util.List;
import java.util.Map;

import org.macula.cloud.core.utils.SecurityUtils;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedAuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.jwt.crypto.sign.SignerVerifier;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

public class SubjectPrincipalUserInfoTokenServices extends UserInfoTokenServices {

	private SignerVerifier signer;
	private String clientId;
	private AuthoritiesExtractor authoritiesExtractor = new FixedAuthoritiesExtractor();

	public SubjectPrincipalUserInfoTokenServices(String userInfoEndpointUrl, String clientId, PrincipalExtractor principalExtractor,
			AuthoritiesExtractor authoritiesExtractor, SignerVerifier signer) {
		super(userInfoEndpointUrl, clientId);
		if (principalExtractor != null) {
			super.setPrincipalExtractor(principalExtractor);
		}
		if (authoritiesExtractor != null) {
			super.setAuthoritiesExtractor(authoritiesExtractor);
			this.authoritiesExtractor = authoritiesExtractor;
		}
		this.clientId = clientId;
		this.signer = signer;
		BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
		resource.setClientId(this.clientId);
		OAuth2RestTemplate template = new OAuth2RestTemplate(resource);
		setRestTemplate(template);
	}

	@Override
	// @Cacheable(cacheNames = OAuth2AccessToken.ACCESS_TOKEN, key = "#accessToken", unless = "#result == null")
	public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
		if (accessToken.indexOf(".") > 0) {
			// JWT token
			Map<String, Object> principalMap = SecurityUtils.convertMap(accessToken, signer);
			return extractAuthentication2(principalMap);
		}
		return super.loadAuthentication(accessToken);
	}

	private OAuth2Authentication extractAuthentication2(Map<String, Object> map) {
		Object principal = getPrincipal(map);
		List<GrantedAuthority> authorities = authoritiesExtractor.extractAuthorities(map);
		OAuth2Request request = new OAuth2Request(null, this.clientId, null, true, null, null, null, null, null);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
		token.setDetails(map);
		return new OAuth2Authentication(request, token);
	}

}
