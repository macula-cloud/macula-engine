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
@Getter
@Setter
public class CompanyAsset extends BaseEntity {

	private static final long serialVersionUID = Versions.serialVersion;

	@Column(name = "COMPANY_ID")
	private String companyId;

	public CompanyAsset clone(CompanyAsset entity) {
		super.clone(entity);
		entity.setCompanyId(getCompanyId());
		return entity;
	}

}
