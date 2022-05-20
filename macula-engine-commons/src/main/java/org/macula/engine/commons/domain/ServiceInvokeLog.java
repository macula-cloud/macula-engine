package org.macula.engine.commons.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(callSuper = true)
@Getter
@Setter
@MappedSuperclass
public class ServiceInvokeLog extends AbstractAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 调用Key值（业务Id）
	 */
	private String dataKey;

	/**
	 * 调用描述
	 */
	private String description;

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
	 * 异常信息
	 */
	@Lob
	private String exceptionMessage;

	/**
	 * 串联起来的ID
	 */
	private String transactionId;

	/**
	 * 备注信息
	 */
	private String comments;

	public ServiceInvokeLog() {

	}

	public ServiceInvokeLog(Long id) {
		this.setId(id);
	}

	public <T extends ServiceInvokeLog> ServiceInvokeLog clone(T entity) {
		super.clone(entity);
		if (getDataKey() != null) {
			entity.setDataKey(getDataKey());
		}
		if (getSource() != null) {
			entity.setSource(getSource());
		}
		if (getSourceMethod() != null) {
			entity.setSourceMethod(getSourceMethod());
		}
		if (getSourceMessage() != null) {
			entity.setSourceMessage(getSourceMessage());
		}
		if (getSourceTimestamp() != null) {
			entity.setSourceTimestamp(getSourceTimestamp());
		}
		if (getTarget() != null) {
			entity.setTarget(getTarget());
		}
		if (getTargetMethod() != null) {
			entity.setTargetMethod(getTargetMethod());
		}
		if (getTargetMessage() != null) {
			entity.setTargetMessage(getTargetMessage());
		}
		if (getTargetTimestamp() != null) {
			entity.setTargetTimestamp(getTargetTimestamp());
		}
		if (getNode() != null) {
			entity.setNode(getNode());
		}
		if (getStatus() != null) {
			entity.setStatus(getStatus());
		}
		if (getExceptionMessage() != null) {
			entity.setExceptionMessage(getExceptionMessage());
		}
		if (getTransactionId() != null) {
			entity.setTransactionId(getTransactionId());
		}
		if (getComments() != null) {
			entity.setComments(getComments());
		}
		return entity;
	}
}
