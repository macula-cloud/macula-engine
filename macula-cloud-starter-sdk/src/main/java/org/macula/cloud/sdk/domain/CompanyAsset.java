package org.macula.cloud.sdk.domain;

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
public class CompanyAsset extends Entity {

	private static final long serialVersionUID = 1L;

	@Column(name = "COMPANY_ID")
	private String companyId;

	public CompanyAsset clone(CompanyAsset entity) {
		super.clone(entity);
		entity.setCompanyId(getCompanyId());
		return entity;
	}

}
