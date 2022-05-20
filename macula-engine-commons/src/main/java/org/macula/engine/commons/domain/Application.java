package org.macula.engine.commons.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
@ToString(callSuper = true)
@Getter
@Setter
public class Application extends TenantAsset {

	private static final long serialVersionUID = 1L;

	@Column(name = "APP_GROUP")
	private String group;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "HOMEPAGE")
	private String homePage;

	@Column(name = "INFORMATION_URL")
	private String informationUrl;

	@Column(name = "LOGO")
	private String logo;

	@Column(name = "LOGOUT_URL")
	private String logoutUrl;

	// CAS属性
	@Column(name = "SERVICE_ID")
	private String serviceId;

	@Column(name = "THEME")
	private String theme;

	// OAuth2属性
	@Column(name = "CLIENT_ID")
	private String clientId;

	@Column(name = "CLIENT_SECRET")
	private String clientSecret;

	@Column(name = "RESOURCE_IDS")
	private String resourceIds;

	@Column(name = "AUTHORITIES")
	private String authorities;

	@Column(name = "SCOPE")
	private String scope;

	@Column(name = "SUPPORTED_GRANTS")
	private String supportedGrantTypes;

	@Column(name = "SUPPORTED_RESPONSES")
	private String supportedResponseTypes;

	@Column(name = "AUTO_APPROVE_SCOPES")
	private String autoApproveScopes;

	@Column(name = "ACCESS_TOKEN_VALIDITY")
	private int accessTokenValidity = 3600;

	@Column(name = "REFRESH_TOKEN_VALIDITY")
	private int refreshTokenValidity = 3600;

	@Column(name = "LOGIN_REDIRECT_URI")
	private String loginRedirectUri;

	@Column(name = "ADDITIONAL_INFORMATION")
	private String additionalInformation;

	public void clone(Application entity) {
		super.clone(entity);
		entity.setGroup(getGroup());
		entity.setDescription(getDescription());
		entity.setHomePage(getHomePage());
		entity.setInformationUrl(getInformationUrl());
		entity.setLogo(getLogo());
		entity.setLogoutUrl(getLogoutUrl());
		entity.setServiceId(getServiceId());
		entity.setTheme(getTheme());

		entity.setClientId(getClientId());
		entity.setClientSecret(getClientSecret());
		entity.setResourceIds(getResourceIds());
		entity.setAuthorities(getAuthorities());
		entity.setScope(getScope());
		entity.setSupportedGrantTypes(getSupportedGrantTypes());
		entity.setSupportedResponseTypes(getSupportedResponseTypes());
		entity.setAutoApproveScopes(getAutoApproveScopes());
		entity.setAccessTokenValidity(getAccessTokenValidity());
		entity.setRefreshTokenValidity(getRefreshTokenValidity());
		entity.setLoginRedirectUri(getLoginRedirectUri());
		entity.setAdditionalInformation(getAdditionalInformation());
	}

}
