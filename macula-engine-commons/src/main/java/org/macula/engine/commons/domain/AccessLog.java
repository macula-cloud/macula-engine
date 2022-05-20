package org.macula.engine.commons.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
@Setter
@Getter
@ToString(callSuper = true)
public class AccessLog extends AbstractAuditable<String> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "RESOURCE_ID", nullable = false)
	@NotNull
	private Long resourceId;
	@Column(name = "SUBJECT_TYPE", nullable = false, length = 50)
	@NotNull
	private String subjectType;
	@Column(name = "SUBJECT", nullable = false, length = 50)
	@NotNull
	private String subject;
	@Column(name = "START_TIME", nullable = false)
	@NotNull
	private Date startTime;
	@Column(name = "END_TIME", nullable = false)
	@NotNull
	private Date endTime;
	@Column(name = "REQUEST_ADDR", nullable = false, length = 50)
	@NotNull
	private String requestAddress;
	@Column(name = "REQUEST_URL", nullable = false, length = 1024)
	@NotNull
	private String requestURL;
	@Column(name = "APP_ID", nullable = false, length = 50)
	@NotNull
	private String application;
	@Column(name = "APP_INST", nullable = false, length = 50)
	@NotNull
	private String instance;
	@Column(name = "REQUEST_DETAIL", columnDefinition = "CLOB")
	private String requestDetail;
	@Column(name = "RESPONSE_CODE", length = 10)
	private String responseCode;
	@Column(name = "ERROR_MESSAGE", columnDefinition = "CLOB")
	private String errorMessage;

	public AccessLog clone(AccessLog entity) {
		super.clone(entity);
		entity.setResourceId(getResourceId());
		entity.setSubjectType(getSubjectType());
		entity.setSubject(getSubject());
		entity.setStartTime(getStartTime());
		entity.setEndTime(getEndTime());
		entity.setRequestAddress(getRequestAddress());
		entity.setRequestURL(getRequestURL());
		entity.setApplication(getApplication());
		entity.setInstance(getInstance());
		entity.setRequestDetail(getRequestDetail());
		entity.setResponseCode(getResponseCode());
		entity.setErrorMessage(getErrorMessage());
		return entity;
	}

}
