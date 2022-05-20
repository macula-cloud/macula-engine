package org.macula.engine.commons.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Client {

	private String appId;

	private String appName;

	private String homePage;

	private String appKey;

	private String appSecret;

	public Client() {
	}

	public Client(Long id, String appId, String appName, String homePage, String appKey, String appSecret) {
		this.appId = appId;
		this.appName = appName;
		this.homePage = homePage;
		this.appKey = appKey;
		this.appSecret = appSecret;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
}
