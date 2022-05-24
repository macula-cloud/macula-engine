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
public class TenantAsset extends BaseEntity {

	private static final long serialVersionUID = Versions.serialVersion;

	@Column(name = "TENANT_ID")
	private String tenantId;

	public TenantAsset clone(TenantAsset entity) {
		super.clone(entity);
		entity.setTenantId(getTenantId());
		return entity;
	}
}
