package org.macula.cloud.sdk.principal;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString(callSuper = true)
public final class SubjectCredential implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;

	private String password;

	private String source;

	@Builder.Default
	private SubjectType type = SubjectType.USER;

	@Builder.Default
	private Map<String, Object> properties = new LinkedHashMap<>();

	public void makePasswordProtected() {
		this.password = "<-protected->";
	}
}
