package org.macula.cloud.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
@ToString(callSuper = true)
@Getter
@Setter
public class ServiceInvokeLog extends AbstractAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 调用Key值（业务Id）
	 */
	private String key;

	/**
	 * 调用方服务名
	 */
	private String source;

	/**
	 * 调用方方法标记
	 */
	private String sourceMethod;

	/**
	 * 调用参数
	 */
	@Lob
	private String sourceMessage;

	/**
	 * 调用时间
	 */
	private Date sourceTimestamp;

	/**
	 * 目标服务名
	 */
	private String target;

	/**
	 * 目标服务方法
	 */
	private String targetMethod;

	/**
	 * 返回结果
	 */
	@Lob
	private String targetMessage;

	/**
	 * 返回时间
	 */
	private Date targetTimestamp;

	/**
	 * 服务实例
	 */
	private String node;

	/**
	 * 执行状态
	 */
	private String status;

	/**
	 * 异常代码
	 */
	private String statusCode;

	/**
	 * 异常信息
	 */
	@Lob
	private String exceptionMessage;

	public ServiceInvokeLog clone(ServiceInvokeLog entity) {
		super.clone(entity);
		entity.setKey(getKey());
		entity.setSource(getSource());
		entity.setSourceMethod(getSourceMethod());
		entity.setSourceMessage(getSourceMessage());
		entity.setSourceTimestamp(getSourceTimestamp());
		entity.setTarget(target);
		entity.setTargetMethod(getTargetMethod());
		entity.setTargetMessage(getTargetMessage());
		entity.setTargetTimestamp(getTargetTimestamp());
		entity.setNode(getNode());
		entity.setStatus(getStatus());
		entity.setStatusCode(getStatusCode());
		entity.setExceptionMessage(getExceptionMessage());
		return entity;
	}
}
