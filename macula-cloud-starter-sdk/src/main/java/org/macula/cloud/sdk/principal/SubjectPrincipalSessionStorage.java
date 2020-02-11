package org.macula.cloud.sdk.principal;

import org.apache.commons.collections4.CollectionUtils;
import org.macula.cloud.sdk.session.Session;
import org.macula.cloud.sdk.session.Sessions;
import org.macula.cloud.sdk.utils.J2CacheUtils;
import org.macula.cloud.sdk.utils.SystemUtils;

public class SubjectPrincipalSessionStorage {

	private String principalsRegion = J2CacheUtils.PRINCIPALS_REGION;
	private String sessionsRegion = J2CacheUtils.SESSIONS_REGION;
	private String sessionPrefix = "US:";
	private String sessionsPrefix = "USS:";
	private String principalPrefix = "UP:";

	public boolean validate(Session userSession, long timestamp) {
		return userSession == null || userSession.getLastUpdatedTime() > timestamp;
	}

	public boolean validate(SubjectPrincipal subjectPrincipal, long timestamp) {
		return subjectPrincipal == null || subjectPrincipal.getLastUpdatedTime() > timestamp;
	}

	public SubjectPrincipal checkoutPrincipal(String guid) {
		return (SubjectPrincipal) J2CacheUtils.get(principalsRegion, getPrincipalKey(guid));
	}

	public Session checkoutSession(String sessionId) {
		return (Session) J2CacheUtils.get(sessionsRegion, getSessionKey(sessionId));
	}

	public String commit(SubjectPrincipal subjectPrincipal) {
		String guid = subjectPrincipal.getGuid();
		subjectPrincipal.setLastUpdatedTime(SystemUtils.getCurrentTime().getTime());
		J2CacheUtils.set(principalsRegion, getPrincipalKey(guid), subjectPrincipal);
		return guid;
	}

	public Sessions checkoutSessions(String guid) {
		return (Sessions) J2CacheUtils.get(sessionsRegion, getSessionsKey(guid));
	}

	public Session commit(Session session) {
		J2CacheUtils.set(sessionsRegion, getSessionKey(session.getSessionId()), session);
		Sessions sessions = checkoutSessions(session.getGuid());
		if (sessions == null) {
			sessions = new Sessions(session.getGuid());
		}
		sessions.addUserSession(session);
		commit(sessions);
		return session;
	}

	public Sessions commit(Sessions sessions) {
		J2CacheUtils.set(sessionsRegion, getSessionsKey(sessions.getguid()), sessions);
		return sessions;
	}

	public String destory(Session session) {
		String guid = session.getGuid();
		String sessionId = session.getSessionId();
		Sessions sessions = checkoutSessions(guid);
		if (sessions != null) {
			sessions.removeSessionIds(session.getSessionId());
			if (CollectionUtils.isEmpty(sessions.getSessoinIds())) {
				J2CacheUtils.evict(sessionsRegion, getSessionsKey(sessions.getguid()));
			} else {
				commit(sessions);
			}
		}
		J2CacheUtils.evict(sessionsRegion, getSessionKey(sessionId));
		return sessionId;
	}

	public String destory(SubjectPrincipal subjectPrincipal) {
		String guid = subjectPrincipal.getGuid();
		J2CacheUtils.evict(principalsRegion, getPrincipalKey(guid));
		return guid;
	}

	private String getPrincipalKey(String id) {
		return this.principalPrefix.concat(id);
	}

	private String getSessionKey(String id) {
		return this.sessionPrefix.concat(id);
	}

	private String getSessionsKey(String id) {
		return this.sessionsPrefix.concat(id);
	}
}
