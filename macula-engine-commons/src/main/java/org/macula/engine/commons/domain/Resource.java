package org.macula.engine.commons.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;
import org.macula.engine.assistant.constants.Versions;

@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
@ToString(callSuper = true)
public class Resource extends ApplicationAsset {

	private static final long serialVersionUID = Versions.serialVersion;

	@Column(name = "TYPE", nullable = false)
	private String type;

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	@Column(name = "URL", nullable = false)
	private String url;

	@Column(name = "ICON", nullable = false)
	private String icon;

	@Column(name = "IS_VISIBLE", nullable = false)
	private boolean visible;

	@Column(name = "ORERED", nullable = false)
	private int ordered;

	public Resource clone(Resource entity) {
		super.clone(entity);
		entity.setType(getType());
		entity.setDescription(getDescription());
		entity.setUrl(getUrl());
		entity.setIcon(getIcon());
		entity.setVisible(isVisible());
		entity.setOrdered(getOrdered());
		return entity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getOrdered() {
		return ordered;
	}

	public void setOrdered(int ordered) {
		this.ordered = ordered;
	}

}
