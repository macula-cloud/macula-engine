package org.macula.cloud.sdk.principal;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import lombok.ToString;

@ToString
public class SubjectAuthentication implements Authentication {

	private static final long serialVersionUID = 1L;

	private final SubjectPrincipal principal;

	public SubjectAuthentication(SubjectPrincipal principal) {
		this.principal = principal;
	}

	@Override
	public String getName() {
		return principal.getName();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList(principal.getAuthorities().toArray(new String[0]));
	}

	@Override
	public Object getCredentials() {
		return principal.getCredential();
	}

	@Override
	public Object getDetails() {
		return principal;
	}

	@Override
	public SubjectPrincipal getPrincipal() {
		return principal;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		// Nothing
	}

}
