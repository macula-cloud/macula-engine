package org.macula.cloud.core.domain;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import org.springframework.data.domain.Persistable;

import lombok.ToString;

@MappedSuperclass
@ToString(callSuper = true)
public abstract class AbstractPersistable<PK extends Serializable>
		extends org.springframework.data.jpa.domain.AbstractPersistable<PK> implements Persistable<PK> {

	/** 是否已删除标志 */
	private transient boolean deleted = false;

	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public AbstractPersistable<PK> clone(AbstractPersistable<PK> entity) {
		entity.setId(getId());
		entity.setDeleted(isDeleted());
		return entity;
	}
}