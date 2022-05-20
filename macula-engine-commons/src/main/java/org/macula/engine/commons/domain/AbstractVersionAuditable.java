package org.macula.engine.commons.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import lombok.ToString;

@MappedSuperclass
@ToString(callSuper = true)
public abstract class AbstractVersionAuditable<PK extends Serializable> extends AbstractAuditable<PK> {

	@Version
	@Column(name = "OBJECT_VERSION_NUMBER", nullable = false, length = 50)
	private Long version;

	public AbstractVersionAuditable<PK> clone(AbstractVersionAuditable<PK> entity) {
		super.clone(entity);
		if (getVersion() != null) {
			entity.setVersion(getVersion());
		}
		return entity;
	}

	/**
	 * @return
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * @param version
	 */
	public void setVersion(Long version) {
		this.version = version;
	}
}
