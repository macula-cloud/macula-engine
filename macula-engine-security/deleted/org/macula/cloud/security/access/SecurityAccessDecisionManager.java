package org.macula.cloud.security.access;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

public class SecurityAccessDecisionManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {

		if (CollectionUtils.isEmpty(configAttributes)) {
			return;
		}

		for (ConfigAttribute configAttribute : configAttributes) {
			String needRole = configAttribute.getAttribute();
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (needRole.trim().equals(ga.getAuthority())) {
					return;
				}
			}
		}
		
		throw new AccessDeniedException("访问被拒绝，权限不足");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
