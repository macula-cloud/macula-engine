package org.macula.cloud.core.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Auditable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.util.ProxyUtils;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class LegacyUpdateAuditable<PK extends Serializable> implements Auditable<String, PK, Instant>, Persistable<PK>, Serializable {

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
	private Date createdDate;

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

		LegacyUpdateAuditable<?> that = (LegacyUpdateAuditable<?>) obj;

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

	public LegacyUpdateAuditable<PK> clone(LegacyUpdateAuditable<PK> entity) {
		// entity.setId(getId());
		entity.setDeleted(isDeleted());
		return entity;
	}

	public void cloneId(LegacyUpdateAuditable<PK> entity) {
		entity.setId(getId());
	}

	@Override
	public Optional<String> getCreatedBy() {
		return Optional.ofNullable(createdBy);
	}

	@Override
	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public Optional<Instant> getCreatedDate() {
		if (createdDate == null) {
			return Optional.empty();
		}
		return Optional.of(Instant.ofEpochMilli(createdDate.getTime()));
	}

	@Override
	public Optional<String> getLastModifiedBy() {
		return Optional.ofNullable(lastUpdatedBy);
	}

	@Override
	public void setLastModifiedBy(final String lastModifiedBy) {
		this.lastUpdatedBy = lastModifiedBy;
	}

	@Override
	public Optional<Instant> getLastModifiedDate() {
		if (lastUpdatedTime == null) {
			return Optional.empty();
		}
		return Optional.of(Instant.ofEpochMilli(lastUpdatedTime.getTime()));
	}

	@Override
	public void setCreatedDate(Instant creationDate) {
		if (creationDate != null) {
			this.createdDate = new Date(creationDate.toEpochMilli());
		}
	}

	@Override
	public void setLastModifiedDate(Instant lastModifiedDate) {
		if (lastModifiedDate != null) {
			this.lastUpdatedTime = new Date(lastModifiedDate.toEpochMilli());
		}
	}

}
