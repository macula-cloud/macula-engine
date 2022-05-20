package org.macula.engine.commons.domain;

import javax.persistence.Column;
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
public class ApplicationAsset extends StructuredEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "APPLICATION_ID")
	private String applicationId;

	public ApplicationAsset clone(ApplicationAsset entity) {
		super.clone(entity);
		entity.setApplicationId(getApplicationId());
		return entity;
	}

}
