package org.macula.engine.commons.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;
import org.macula.engine.assistant.constants.Versions;

/**
 * <p>
 * <b>StructuredEntity</b> 是结构化实体.
 * </p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
@ToString(callSuper = true)
public class StructuredEntity extends BaseEntity {

	private static final long serialVersionUID = Versions.serialVersion;

	@Column(name = "IS_GROUP", nullable = false)
	private boolean group;

	@Transient
	private StructuredEntity parent;

	@Transient
	private List<StructuredEntity> children = new ArrayList<StructuredEntity>();

	@JsonIgnore
	public StructuredEntity getParent() {
		return parent;
	}

	@JsonIgnore
	public List<StructuredEntity> getChildren() {
		return children;
	}

	public Long getParentId() {
		return getParent() != null ? getParent().getId() : null;
	}

	public List<Long> getChildrenIds() {
		return getChildren() != null ? getChildren().stream().map(BaseEntity::getId).collect(Collectors.toList()) : null;
	}

	public boolean isGroup() {
		return group;
	}

	public void setGroup(boolean group) {
		this.group = group;
	}

	public void setParent(StructuredEntity parent) {
		this.parent = parent;
	}

	public void setChildren(List<StructuredEntity> children) {
		this.children = children;
	}

	public StructuredEntity clone(StructuredEntity entity) {
		super.clone(entity);
		entity.setGroup(isGroup());
		entity.setParent(getParent());
		entity.setChildren(getChildren());
		return entity;
	}
}
