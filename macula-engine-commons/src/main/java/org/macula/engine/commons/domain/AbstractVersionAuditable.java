package org.macula.engine.commons.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

import lombok.ToString;

@MappedSuperclass
@ToString(callSuper = true)
public abstract class AbstractVersionAuditable<PK extends Serializable> extends AbstractAuditable<PK> {

	@Version
	@Column(name = "REVERSION", nullable = false, length = 50)
	private Long reversion;

	public AbstractVersionAuditable<PK> clone(AbstractVersionAuditable<PK> entity) {
		super.clone(entity);
		entity.setReversion(getReversion());
		return entity;
	}

	/**
	 * @return
	 */
	public Long getReversion() {
		return reversion;
	}

	/**
	 * @param version
	 */
	public void setReversion(Long version) {
		this.reversion = version;
	}
}
