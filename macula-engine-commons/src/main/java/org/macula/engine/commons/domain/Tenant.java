package org.macula.engine.commons.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;
import org.macula.engine.assistant.constants.Versions;

@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
@ToString(callSuper = true)
public class Tenant extends BaseEntity {

	private static final long serialVersionUID = Versions.serialVersion;

	@Column(name = "TENANT_TYPE")
	private Type type;

	public static enum Type {
		USER,
		DEPARTMENT,
		COMPANY
	}

	public Tenant clone(Tenant entity) {
		super.clone(entity);
		entity.setType(getType());
		return entity;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
