package org.macula.cloud.core.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
@ToString(callSuper = true)
public class Entity extends AbstractAuditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CODE", nullable = false, length = 50)
	@NotNull
	private String code;
	@Column(name = "NAME", nullable = false, length = 50)
	@NotNull
	private String name;

	@Transient
	private Map<String, Serializable> attributes;

	public void setAttributes(Map<String, Serializable> attributes) {
		if (attributes != null) {
			this.attributes = new HashMap<String, Serializable>();
			this.attributes.putAll(attributes);
		} else {
			this.attributes = null;
		}
	}

	@Transient
	public Map<String, Serializable> getAttributes() {
		if (attributes == null) {
			return Collections.emptyMap();
		}
		return Collections.unmodifiableMap(attributes);
	}

	public void addAttribute(String key, Serializable value) {
		if (attributes == null) {
			this.attributes = new HashMap<String, Serializable>();
		}
		this.attributes.put(key, value);
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public Entity clone(Entity entity) {
		super.clone(entity);
		entity.setCode(getCode());
		entity.setName(getName());
		entity.setAttributes(getAttributes());
		return entity;
	}

}
