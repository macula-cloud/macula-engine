package org.macula.engine.security.oauth2;

import java.io.Serializable;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.macula.engine.assistant.constants.Versions;

@Getter
@Setter
@ToString
public class OAuth2Client implements Serializable {

	private static final long serialVersionUID = Versions.serialVersion;

	private String clientId;
	private Set<String> clientAuthorizedGrantTypes;
	private Set<String> clientResourceIds;
	private Set<String> clientScope;
	private Set<String> clientRegisteredRedirectUri;
	private Integer clientAccessTokenValiditySeconds;
	private Integer clientRefreshTokenValiditySeconds;
	private Set<String> clientAutoApproveScopes;
}
