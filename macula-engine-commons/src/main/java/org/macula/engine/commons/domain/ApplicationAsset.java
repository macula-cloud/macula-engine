package org.macula.engine.commons.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.macula.engine.assistant.constants.Versions;

@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
@ToString(callSuper = true)
@Getter
@Setter
public class ApplicationAsset extends BaseEntity {

	private static final long serialVersionUID = Versions.serialVersion;

	@Column(name = "APPLICATION_ID")
	private String applicationId;

	public ApplicationAsset clone(ApplicationAsset entity) {
		super.clone(entity);
		entity.setApplicationId(getApplicationId());
		return entity;
	}

}
