package org.macula.cloud.sdk.session;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.macula.cloud.sdk.utils.StringUtils;

public class Sessions implements Serializable {

	private static final long serialVersionUID = 1L;

	private String guid;
	private Set<String> sessoinIds = new HashSet<String>();

	public Sessions(String guid, String... sessionIds) {
		this.guid = guid;
		if (sessionIds != null) {
			this.sessoinIds.addAll(Arrays.asList(sessionIds));
		}
	}

	public void addUserSession(Session session) {
		if (session != null && StringUtils.equals(this.getguid(), session.getGuid())) {
			addSessoinIds(session.getSessionId());
		}
	}

	public String getguid() {
		return guid;
	}

	public void setguid(String guid) {
		this.guid = guid;
	}

	public Collection<String> getSessoinIds() {
		return Collections.unmodifiableSet(sessoinIds);
	}

	public void addSessoinIds(String... sessionIds) {
		if (sessionIds != null) {
			this.sessoinIds.addAll(Arrays.asList(sessionIds));
		}
	}

	public void removeSessionIds(String... sessionIds) {
		if (sessionIds != null) {
			this.sessoinIds.removeAll(Arrays.asList(sessionIds));
		}
	}

}
