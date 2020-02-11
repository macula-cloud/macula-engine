package org.macula.cloud.sdk.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @Auther: Aaron
 * @Date: 2018/12/5 15:11
 * @Description:
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SysApplication {

	private String appId;

	private String appName;

	private String homePage;

	private String appKey;

	private String appSecret;

	public SysApplication() {
	}

	public SysApplication(Long id, String appId, String appName, String homePage, String appKey, String appSecret) {
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
