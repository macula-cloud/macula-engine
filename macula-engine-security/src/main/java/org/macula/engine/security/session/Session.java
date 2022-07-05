package org.macula.engine.security.session;

import java.io.Serializable;
import java.util.Locale;
import java.util.TimeZone;

import org.macula.engine.assistant.constants.Versions;

public class Session implements Serializable {

	private static final long serialVersionUID = Versions.serialVersion;

	private final String subjectId;

	private final String sessionId;
	private String loginName;
	private String accessKey;
	private String authType;
	private String terminalType;
	private Locale locale;
	private TimeZone timeZone;
	private long createdTime;
	private long lastUpdatedTime;

	public Session(String subjectId, String sessionId) {
		this.subjectId = subjectId;
		this.sessionId = sessionId;
		this.setLocale(Locale.getDefault());
		this.setTimeZone(TimeZone.getDefault());
		this.createdTime = System.currentTimeMillis();
		this.lastUpdatedTime = System.currentTimeMillis();
	}

	public String getSubjectId() {
		return subjectId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	/**
	 * 获取登录凭据验证方式
	 */
	public String getAuthType() {
		return this.authType;
	}

	/**
	 * 获取登录终端类型
	 */
	public String getTerminalType() {
		return this.terminalType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	/** 获取最后使用时间 */
	public long getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(long lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public long getCreatedTime() {
		return createdTime;
	}

}
