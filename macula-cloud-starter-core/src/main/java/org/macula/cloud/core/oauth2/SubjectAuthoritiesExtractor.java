package org.macula.cloud.core.oauth2;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedAuthoritiesExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;

public class SubjectAuthoritiesExtractor extends FixedAuthoritiesExtractor {
	private String defaultRole;

	public SubjectAuthoritiesExtractor(String defaultRole) {
		this.defaultRole = defaultRole;
	}

	@Override
	public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
		List<GrantedAuthority> authorities = super.extractAuthorities(map);
		if (CollectionUtils.isEmpty(authorities) && StringUtils.isNotEmpty(defaultRole)) {
			authorities = Collections.singletonList(new SimpleGrantedAuthority(defaultRole));
		}
		return authorities;
	}
}
