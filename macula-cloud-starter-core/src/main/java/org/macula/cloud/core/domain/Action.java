package org.macula.cloud.core.domain;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.macula.cloud.core.utils.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
@ToString(callSuper = true)
public class Action extends Resource {

	private static final long serialVersionUID = 1L;

	@Column(name = "LOG_LEVEL", nullable = false)
	@NotNull
	private String logLevel;

	@Column(name = "LOG_OPTION", length = 10, nullable = false)
	private int logOption = 0;

	@Column(name = "MID")
	private Long mid;

	public Action clone(Action action) {
		super.clone(action);
		action.setLogLevel(getLogLevel());
		action.setLogOption(getLogOption());
		action.setMid(getMid());
		return action;
	}

	@Override
	public Map<String, Serializable> getAttributes() {
		Map<String, Serializable> map = super.getAttributes();
		map.put("logOption", getLogOption());
		map.put("logLevel", getLogLevel());
		map.put("mid", getMid());
		map.put("ordered", getOrdered());
		return map;
	}

	public void setLogOption(String option) {
		if (StringUtils.isNotBlank(option)) {
			String[] options = option.split(",");
			int tmpValue = 0;
			for (int i = 0; i < options.length; i++) {
				tmpValue += Integer.valueOf(options[i]);
			}
			this.logOption = tmpValue;
		}
	}

	/**
	 * @return the logLevel
	 */
	public String getLogLevel() {
		return logLevel;
	}

	/**
	 * @param logLevel the logLevel to set
	 */
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	/**
	 * @return the logOption
	 */
	public int getLogOption() {
		return logOption;
	}

	/**
	 * @param logOption the logOption to set
	 */
	public void setLogOption(int logOption) {
		this.logOption = logOption;
	}

	/**
	 * @return the mid
	 */
	public Long getMid() {
		return mid;
	}

	/**
	 * @param mid the mid to set
	 */
	public void setMid(Long mid) {
		this.mid = mid;
	}

}