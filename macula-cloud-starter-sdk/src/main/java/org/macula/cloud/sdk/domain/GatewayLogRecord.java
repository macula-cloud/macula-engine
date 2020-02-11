package org.macula.cloud.sdk.domain;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
@ToString(callSuper = true)
public class GatewayLogRecord extends AbstractAuditable<String> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String url;
	private String remoteAddress;
	private String method;
	private Integer statusCode;
	private String username;
	private String application;
	private Long startTime;
	private Long endTime;
	private String header;
	private String uri;
	private String uriId;

	public GatewayLogRecord clone(GatewayLogRecord entity) {
		super.clone(entity);
		entity.setUrl(getUrl());
		entity.setRemoteAddress(getRemoteAddress());
		entity.setMethod(getMethod());
		entity.setStatusCode(getStatusCode());
		entity.setUsername(getUsername());
		entity.setApplication(getApplication());
		entity.setStartTime(getStartTime());
		entity.setEndTime(getEndTime());
		entity.setHeader(getHeader());
		entity.setUri(getUri());
		entity.setUriId(getUriId());
		return entity;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUriId() {
		return uriId;
	}

	public void setUriId(String uriId) {
		this.uriId = uriId;
	}
}
