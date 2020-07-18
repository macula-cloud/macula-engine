package org.macula.cloud.core.oauth2;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.macula.cloud.core.utils.SecurityUtils;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedAuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.jwt.crypto.sign.SignerVerifier;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

public class SubjectPrincipalUserInfoTokenServices extends UserInfoTokenServices {

	private SignerVerifier signer;
	private String clientId;
	private AuthoritiesExtractor authoritiesExtractor = new FixedAuthoritiesExtractor();
	private String userInfoEndpointUrl;
	private OAuth2RestTemplate restTemplate;
	private String tokenType = DefaultOAuth2AccessToken.BEARER_TYPE;

	public SubjectPrincipalUserInfoTokenServices(String userInfoEndpointUrl, String clientId, PrincipalExtractor principalExtractor,
			AuthoritiesExtractor authoritiesExtractor, SignerVerifier signer) {
		super(userInfoEndpointUrl, clientId);
		this.userInfoEndpointUrl = userInfoEndpointUrl;
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
		this.restTemplate = template;
	}

	@Override
	@Cacheable(cacheNames = OAuth2AccessToken.ACCESS_TOKEN, key = "#accessToken", unless = "#result == null")
	public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
		if (accessToken.indexOf(".") > 0) {
			// JWT token
			Map<String, Object> principalMap = SecurityUtils.convertMap(accessToken, signer);
			return customExtractAuthentication(principalMap);
		}
		return loadInternalAuthentication(accessToken);
	}

	public OAuth2Authentication loadInternalAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
		Map<String, Object> map = customGetMap(this.userInfoEndpointUrl, accessToken);
		if (map.containsKey("error")) {
			if (this.logger.isDebugEnabled()) {
				this.logger.debug("userinfo returned error: " + map.get("error"));
			}
			throw new InvalidTokenException(accessToken);
		}
		return customExtractAuthentication(map);
	}

	@SuppressWarnings({ "unchecked" })
	private Map<String, Object> customGetMap(String path, String accessToken) {
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("Getting user info from: " + path);
		}
		try {
			OAuth2RestOperations restTemplate = this.restTemplate;
			if (restTemplate == null) {
				BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
				resource.setClientId(this.clientId);
				restTemplate = new OAuth2RestTemplate(resource);
			}
			OAuth2AccessToken existingToken = restTemplate.getOAuth2ClientContext().getAccessToken();
			if (existingToken == null || !accessToken.equals(existingToken.getValue())) {
				DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(accessToken);
				token.setTokenType(this.tokenType);
				restTemplate.getOAuth2ClientContext().setAccessToken(token);
			}
			return restTemplate.getForEntity(path, Map.class).getBody();
		} catch (Exception ex) {
			this.logger.warn("Could not fetch user details: " + ex.getClass() + ", " + ex.getMessage());
			return Collections.<String, Object>singletonMap("error", "Could not fetch user details");
		}
	}

	private OAuth2Authentication customExtractAuthentication(Map<String, Object> map) {
		Object principal = getPrincipal(map);
		List<GrantedAuthority> authorities = this.authoritiesExtractor.extractAuthorities(map);
		OAuth2Request request = new OAuth2Request(null, this.clientId, null, true, null, null, null, null, null);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
		// token.setDetails(map);
		return new OAuth2Authentication(request, token);
	}

}
