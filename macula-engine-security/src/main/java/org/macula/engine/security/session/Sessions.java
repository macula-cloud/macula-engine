package org.macula.engine.security.session;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.macula.engine.assistant.constants.Versions;
import org.macula.engine.assistant.utils.StringUtils;

public class Sessions implements Serializable {

	private static final long serialVersionUID = Versions.serialVersion;

	private String subjectId;
	private Set<String> sessoinIds = new HashSet<String>();

	public Sessions(String subjectId, String... sessionIds) {
		this.subjectId = subjectId;
		if (sessionIds != null) {
			this.sessoinIds.addAll(Arrays.asList(sessionIds));
		}
	}

	public void addUserSession(Session session) {
		if (session != null && StringUtils.equals(this.getSubjectId(), session.getSubjectId())) {
			addSessoinIds(session.getSessionId());
		}
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
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
