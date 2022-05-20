package org.macula.engine.web.properties;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.macula.engine.assistant.constants.BaseConstants;
import org.macula.engine.assistant.exception.MaculaException;
import org.macula.engine.assistant.utils.StringUtils;
import org.macula.engine.web.constants.WebConstants;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Endpoints Properties </p>
 */
@ToString
@Slf4j
@ConfigurationProperties(prefix = WebConstants.PROPERTY_PREFIX_ENDPOINT)
public class EndpointProperties {

	/**
	 * 认证中心服务名称
	 */
	private String uaaServiceName;
	/**
	 * 用户中心服务名称
	 */
	private String upmsServiceName;

	/**
	 * 统一网关服务地址。可以是IP+端口，可以是域名
	 */
	private String gatewayServiceUri;

	/**
	 * 统一认证中心服务地址
	 */
	private String uaaServiceUri;
	/**
	 * 统一权限管理服务地址
	 */
	private String upmsServiceUri;
	/**
	 * OAuth2 Authorization Code 模式认证端点，/oauth2/authorize uri 地址，可修改为自定义地址
	 */
	private String authorizationUri;
	/**
	 * OAuth2 Authorization Code 模式认证端点，/oauth2/authorize端点地址，可修改为自定义地址
	 */
	private String authorizationEndpoint = BaseConstants.DEFAULT_AUTHORIZATION_ENDPOINT;
	/**
	 * OAuth2 /oauth2/token 申请 Token uri 地址，可修改为自定义地址
	 */
	private String accessTokenUri;
	/**
	 * OAuth2 /oauth2/token 申请 Token 端点地址，可修改为自定义地址
	 */
	private String accessTokenEndpoint = BaseConstants.DEFAULT_TOKEN_ENDPOINT;
	/**
	 * OAuth2 /oauth2/jwks uri 地址，可修改为自定义地址
	 */
	private String jwkSetUri;
	/**
	 * OAuth2 /oauth2/jwks 端点地址，可修改为自定义地址
	 */
	private String jwkSetEndpoint = BaseConstants.DEFAULT_JWK_SET_ENDPOINT;
	/**
	 * OAuth2 /oauth2/revoke 撤销 Token uri 地址，可修改为自定义地址
	 */
	private String tokenRevocationUri;
	/**
	 * OAuth2 /oauth2/revoke 撤销 Token 端点地址，可修改为自定义地址
	 */
	private String tokenRevocationEndpoint = BaseConstants.DEFAULT_TOKEN_REVOCATION_ENDPOINT;
	/**
	 * OAuth2 /oauth2/introspect 查看 Token uri地址，可修改为自定义地址
	 */
	private String tokenIntrospectionUri;
	/**
	 * OAuth2 /oauth2/introspect 查看 Token 端点地址，可修改为自定义地址
	 */
	private String tokenIntrospectionEndpoint = BaseConstants.DEFAULT_TOKEN_INTROSPECTION_ENDPOINT;
	/**
	 * OAuth2 OIDC /connect/register uri 地址，可修改为自定义地址
	 */
	private String oidcClientRegistrationUri;
	/**
	 * OAuth2 OIDC /connect/register 端点地址，可修改为自定义地址
	 */
	private String oidcClientRegistrationEndpoint = BaseConstants.DEFAULT_OIDC_CLIENT_REGISTRATION_ENDPOINT;
	/**
	 * OAuth2 OIDC /userinfo uri 地址，可修改为自定义地址
	 */
	private String oidcUserInfoUri;
	/**
	 * OAuth2 OIDC /userinfo 端点地址，可修改为自定义地址
	 */
	private String oidcUserInfoEndpoint = BaseConstants.DEFAULT_OIDC_USER_INFO_ENDPOINT;
	/**
	 * Spring Authorization Server Issuer Url
	 */
	private String issuerUri;

	public String getUaaServiceName() {
		return uaaServiceName;
	}

	public void setUaaServiceName(String uaaServiceName) {
		this.uaaServiceName = uaaServiceName;
	}

	public String getUpmsServiceName() {
		return upmsServiceName;
	}

	public void setUpmsServiceName(String upmsServiceName) {
		this.upmsServiceName = upmsServiceName;
	}

	public String getGatewayServiceUri() {
		return gatewayServiceUri;
	}

	public void setGatewayServiceUri(String gatewayServiceUri) {
		this.gatewayServiceUri = gatewayServiceUri;
	}

	public String getUaaServiceUri() {
		if (StringUtils.isNotBlank(uaaServiceUri)) {
			return uaaServiceUri;
		} else {
			if (StringUtils.isBlank(uaaServiceName)) {
				log.error("[Macula] |- Property [Uaa Service Name] is not set or property format is incorrect!");
				//				throw new PropertyValueIsNotSetException();
				throw new MaculaException("PropertyValueIsNotSetException");
			} else {
				return StringUtils.wellFormed(getGatewayServiceUri()) + uaaServiceName;
			}
		}
	}

	public void setUaaServiceUri(String uaaServiceUri) {
		this.uaaServiceUri = uaaServiceUri;
	}

	private String getDefaultEndpoint(String endpoint, String pathAuthorizationEndpoint) {
		if (StringUtils.isNotBlank(endpoint)) {
			return endpoint;
		} else {
			if (StringUtils.isNotBlank(pathAuthorizationEndpoint)) {
				return getUaaServiceUri() + pathAuthorizationEndpoint;
			} else {
				return getUaaServiceUri();
			}
		}
	}

	public String getUpmsServiceUri() {
		if (StringUtils.isNotBlank(upmsServiceUri)) {
			return upmsServiceUri;
		} else {
			if (StringUtils.isBlank(upmsServiceName)) {
				log.error("[Macula] |- Property [Upms Service Name] is not set or property format is incorrect!");
				// throw new PropertyValueIsNotSetException();
				throw new MaculaException("PropertyValueIsNotSetException");
			} else {
				return StringUtils.wellFormed(getGatewayServiceUri()) + upmsServiceName;
			}
		}
	}

	public void setUpmsServiceUri(String upmsServiceUri) {
		this.upmsServiceUri = upmsServiceUri;
	}

	public String getAuthorizationUri() {
		return getDefaultEndpoint(authorizationUri, getAuthorizationEndpoint());
	}

	public void setAuthorizationUri(String authorizationUri) {
		this.authorizationUri = authorizationUri;
	}

	public String getAccessTokenUri() {
		return getDefaultEndpoint(accessTokenUri, getAccessTokenEndpoint());
	}

	public void setAccessTokenUri(String accessTokenUri) {
		this.accessTokenUri = accessTokenUri;
	}

	public String getJwkSetUri() {
		return getDefaultEndpoint(jwkSetUri, getJwkSetEndpoint());
	}

	public void setJwkSetUri(String jwkSetUri) {
		this.jwkSetUri = jwkSetUri;
	}

	public String getTokenRevocationUri() {
		return getDefaultEndpoint(tokenRevocationUri, getTokenRevocationEndpoint());
	}

	public void setTokenRevocationUri(String tokenRevocationUri) {
		this.tokenRevocationUri = tokenRevocationUri;
	}

	public String getTokenIntrospectionUri() {
		return getDefaultEndpoint(tokenIntrospectionUri, getTokenIntrospectionEndpoint());
	}

	public void setTokenIntrospectionUri(String tokenIntrospectionUri) {
		this.tokenIntrospectionUri = tokenIntrospectionUri;
	}

	public String getOidcClientRegistrationUri() {
		return getDefaultEndpoint(oidcClientRegistrationUri, getOidcClientRegistrationEndpoint());
	}

	public void setOidcClientRegistrationUri(String oidcClientRegistrationUri) {
		this.oidcClientRegistrationUri = oidcClientRegistrationUri;
	}

	public String getOidcUserInfoUri() {
		return getDefaultEndpoint(oidcUserInfoUri, getOidcUserInfoEndpoint());
	}

	public void setOidcUserInfoUri(String oidcUserInfoUri) {
		this.oidcUserInfoUri = oidcUserInfoUri;
	}

	public String getIssuerUri() {
		return this.issuerUri;
	}

	public void setIssuerUri(String issuerUri) {
		this.issuerUri = issuerUri;
	}

	public String getAuthorizationEndpoint() {
		return authorizationEndpoint;
	}

	public void setAuthorizationEndpoint(String authorizationEndpoint) {
		this.authorizationEndpoint = authorizationEndpoint;
	}

	public String getAccessTokenEndpoint() {
		return accessTokenEndpoint;
	}

	public void setAccessTokenEndpoint(String accessTokenEndpoint) {
		this.accessTokenEndpoint = accessTokenEndpoint;
	}

	public String getJwkSetEndpoint() {
		return jwkSetEndpoint;
	}

	public void setJwkSetEndpoint(String jwkSetEndpoint) {
		this.jwkSetEndpoint = jwkSetEndpoint;
	}

	public String getTokenRevocationEndpoint() {
		return tokenRevocationEndpoint;
	}

	public void setTokenRevocationEndpoint(String tokenRevocationEndpoint) {
		this.tokenRevocationEndpoint = tokenRevocationEndpoint;
	}

	public String getTokenIntrospectionEndpoint() {
		return tokenIntrospectionEndpoint;
	}

	public void setTokenIntrospectionEndpoint(String tokenIntrospectionEndpoint) {
		this.tokenIntrospectionEndpoint = tokenIntrospectionEndpoint;
	}

	public String getOidcClientRegistrationEndpoint() {
		return oidcClientRegistrationEndpoint;
	}

	public void setOidcClientRegistrationEndpoint(String oidcClientRegistrationEndpoint) {
		this.oidcClientRegistrationEndpoint = oidcClientRegistrationEndpoint;
	}

	public String getOidcUserInfoEndpoint() {
		return oidcUserInfoEndpoint;
	}

	public void setOidcUserInfoEndpoint(String oidcUserInfoEndpoint) {
		this.oidcUserInfoEndpoint = oidcUserInfoEndpoint;
	}
}
