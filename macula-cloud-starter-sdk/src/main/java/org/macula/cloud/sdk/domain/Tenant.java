package org.macula.cloud.sdk.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
@ToString(callSuper = true)
public class Tenant extends Entity {

	private static final long serialVersionUID = 1L;

	@Column(name = "TENANT_TYPE")
	private Type type;

	public static enum Type {
		USER, DEPARTMENT, COMPANY
	}

	public Tenant clone(Tenant entity) {
		super.clone(entity);
		this.setType(getType());
		return entity;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
