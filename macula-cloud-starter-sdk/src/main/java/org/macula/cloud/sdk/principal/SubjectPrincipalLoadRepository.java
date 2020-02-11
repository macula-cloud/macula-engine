package org.macula.cloud.sdk.principal;

import java.util.ArrayList;
import java.util.List;

public interface SubjectPrincipalLoadRepository {

	public SubjectPrincipal load(SubjectCredential credential);

	public SubjectPrincipal login(SubjectCredential credential);

	public default void postValidate(SubjectPrincipal principal) {
	}

	public default List<SubjectPropertyResolver> getUserPropertyResolvers() {
		return new ArrayList<SubjectPropertyResolver>();
	}

}
