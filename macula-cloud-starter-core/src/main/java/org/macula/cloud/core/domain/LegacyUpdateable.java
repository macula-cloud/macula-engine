package org.macula.cloud.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;
import org.springframework.data.util.ProxyUtils;

@MappedSuperclass
public class LegacyUpdateable<PK extends Serializable> implements Persistable<PK>, Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(generator = "AssignedIdGenerator")
	@GenericGenerator(name = "AssignedIdGenerator", strategy = "org.macula.cloud.core.domain.AssignedIdGenerator")
	@Id
	private @Nullable PK id;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATED_TIME")
	private Date lastUpdatedTime;

	@Transient
	private String createdBy;

	@Transient
	private Date createdTime;

	@Nullable
	@Override
	public PK getId() {
		return id;
	}

	/**
	 * Sets the id of the entity.
	 * @param id the id to set
	 */
	public void setId(@Nullable PK id) {
		this.id = id;
	}

	/**
	 * Must be {@link Transient} in order to ensure that no JPA provider complains because of a missing setter.
	 *
	 * @see org.springframework.data.domain.Persistable#isNew()
	 */
	@Transient // DATAJPA-622
	@Override
	public boolean isNew() {
		return null == getId();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Entity of type %s with id: %s", this.getClass().getName(), getId());
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (null == obj) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (!getClass().equals(ProxyUtils.getUserClass(obj))) {
			return false;
		}

		LegacyUpdateable<?> that = (LegacyUpdateable<?>) obj;

		return null == this.getId() ? false : this.getId().equals(that.getId());
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		int hashCode = 17;

		hashCode += null == getId() ? 0 : getId().hashCode() * 31;

		return hashCode;
	}

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

	public LegacyUpdateable<PK> clone(LegacyUpdateable<PK> entity) {
		// entity.setId(getId());
		entity.setDeleted(isDeleted());
		return entity;
	}

	public void cloneId(LegacyUpdateable<PK> entity) {
		entity.setId(getId());
	}

	/**
	 * @return the lastUpdatedBy
	 */
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	/**
	 * @param lastUpdatedBy the lastUpdatedBy to set
	 */
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdTime
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	/**
	 * @param createdTime the createdTime to set
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
