package org.macula.cloud.core.configure.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.SignerVerifier;
import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityProperties implements Serializable {

	private static final long serialVersionUID = 1L;

	private String feign;

	private String jwtKey = "macula@^cloud$";

	private String ignoreAppIdResource;

	private String needAuthories = "macula-cloud-gateway";

	private boolean includeDefault = true;

	private Long sessionTimeout = 180000L;

	private boolean enabledSingleLogin = false;

	private boolean captcha = true;

	private long captchaTimes = 3;

	private String ignoreUrls = "/static/**,/public/**,/webjars/**,/favicon.ico";

	private String publicUrls = "/actuator,/actuator/**,/login,/login/**,/error,/error**";

	private String loginPath = "/login";

	private String resourcePathPattern = "/api/**";

	private String defaultUrl = "/";

	private String oauth2Callback = "/login/oauth2";

	private boolean oauth2Login = false;

	private boolean standalone = false;

	public Set<String> getIgnoreAppIdResources() {
		if (getIgnoreAppIdResource() != null) {
			return StringUtils.commaDelimitedListToSet(getIgnoreAppIdResource());
		}
		return Collections.emptySet();
	}

	public String[] getIgnorePaths() {
		return StringUtils.commaDelimitedListToStringArray(getIgnoreUrls());
	}

	public String[] getPublicPaths() {
		return StringUtils.commaDelimitedListToStringArray(getPublicUrls());
	}

	public String[] getResourcePaths() {
		return StringUtils.commaDelimitedListToStringArray(getResourcePathPattern());
	}

	public Collection<String> getResourceAuthorities() {
		if (needAuthories != null) {
			return StringUtils.commaDelimitedListToSet(needAuthories);
		}
		return CollectionUtils.emptyCollection();
	}

	public SignerVerifier getJwtSigner() {
		return new MacSigner(getJwtKey());
	}

}
