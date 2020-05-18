
package org.macula.cloud.core.principal;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubjectPrincipal extends User implements Principal, Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;
	private String nickname;
	private String mobile;
	private String email;
	private String avatar;
	private String language;
	private String timeZone;
	private String theme;
	private String organizationId;
	private Set<String> tenantIds;
	private String source;
	private transient Object credential;
	private long lastUpdatedTime = System.currentTimeMillis();

	private String clientId;
	private Set<String> clientAuthorizedGrantTypes;
	private Set<String> clientResourceIds;
	private Set<String> clientScope;
	private Set<String> clientRegisteredRedirectUri;
	private Integer clientAccessTokenValiditySeconds;
	private Integer clientRefreshTokenValiditySeconds;
	private Set<String> clientAutoApproveScopes;
	private Map<String, Serializable> additionInfo = new HashMap<String, Serializable>();

	public SubjectPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	@JsonCreator
	public SubjectPrincipal(@JsonProperty("username") String username, @JsonProperty("password") String password) {
		this(username, password, Collections.emptyList());
	}

	public boolean isUserPrincipal() {
		return getUserId() != null;
	}

	public boolean isClientPrincipal() {
		return !isUserPrincipal() && getClientId() != null;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Set<String> getClientAuthorizedGrantTypes() {
		return clientAuthorizedGrantTypes;
	}

	public void setClientAuthorizedGrantTypes(Collection<String> clientAuthorizedGrantTypes) {
		this.clientAuthorizedGrantTypes = clientAuthorizedGrantTypes == null ? Collections.<String>emptySet()
				: new LinkedHashSet<String>(clientAuthorizedGrantTypes);
	}

	public Set<String> getClientResourceIds() {
		return clientResourceIds;
	}

	public void setClientResourceIds(Collection<String> clientResourceIds) {
		this.clientResourceIds = clientResourceIds == null ? Collections.<String>emptySet() : new LinkedHashSet<String>(clientResourceIds);
	}

	public Set<String> getClientScope() {
		return clientScope;
	}

	public void setClientScope(Collection<String> clientScope) {
		this.clientScope = clientScope == null ? Collections.<String>emptySet() : new LinkedHashSet<String>(clientScope);
	}

	public Set<String> getClientRegisteredRedirectUri() {
		return clientRegisteredRedirectUri;
	}

	public void setClientRegisteredRedirectUri(Collection<String> clientRegisteredRedirectUri) {
		this.clientRegisteredRedirectUri = clientRegisteredRedirectUri == null ? null : new LinkedHashSet<String>(clientRegisteredRedirectUri);
	}

	public Integer getClientAccessTokenValiditySeconds() {
		return clientAccessTokenValiditySeconds;
	}

	public void setClientAccessTokenValiditySeconds(Integer clientAccessTokenValiditySeconds) {
		this.clientAccessTokenValiditySeconds = clientAccessTokenValiditySeconds;
	}

	public Integer getClientRefreshTokenValiditySeconds() {
		return clientRefreshTokenValiditySeconds;
	}

	public void setClientRefreshTokenValiditySeconds(Integer clientRefreshTokenValiditySeconds) {
		this.clientRefreshTokenValiditySeconds = clientRefreshTokenValiditySeconds;
	}

	public Set<String> getClientAutoApproveScopes() {
		return clientAutoApproveScopes;
	}

	public void setClientAutoApproveScopes(Collection<String> clientAutoApproveScopes) {
		this.clientAutoApproveScopes = clientAutoApproveScopes == null ? null : new LinkedHashSet<String>(clientAutoApproveScopes);
	}

	public String getType() {
		return getUserId() != null ? "user" : "client";
	}

	@Override
	public String getName() {
		return getUsername() != null ? getUsername() : getClientId();
	}

	public void addAuthorities(Set<String> authories) {
		// TODO
	}

	public void addAdditionInfo(Map<String, Serializable> properties) {
		// TODO  
	}

	public void addAuthority(String string) {
		// TODO Auto-generated method stub

	}
}
