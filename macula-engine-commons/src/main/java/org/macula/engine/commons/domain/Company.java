package org.macula.engine.commons.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.macula.engine.assistant.constants.Versions;

@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
@ToString(callSuper = true)
@Setter
@Getter
public class Company extends TenantAsset {

	private static final long serialVersionUID = Versions.serialVersion;

	@Column(name = "description")
	private String description;

	public Company clone(Company entity) {
		super.clone(entity);
		entity.setDescription(getDescription());
		return entity;
	}

}
