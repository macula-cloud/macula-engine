package org.macula.engine.commons.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;
import org.macula.engine.assistant.constants.Versions;

@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
@ToString(callSuper = true)
public class GatewayRoute extends AbstractAuditable<Long> implements Serializable {

	private static final long serialVersionUID = Versions.serialVersion;

	@Column(name = "URI")
	private String uri;

	@Column(name = "PATH")
	private String path;

	@Column(name = "STRIP_PREFIX")
	private Integer stripPrefix;

	@Column(name = "ORDERED")
	private int ordered;

	@Column(name = "METHOD")
	private String method;

	@Column(name = "WEIGHT_GROUP")
	private String group;

	@Column(name = "WEIGHT")
	private String weight;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getStripPrefix() {
		return stripPrefix;
	}

	public void setStripPrefix(Integer stripPrefix) {
		this.stripPrefix = stripPrefix;
	}

	public int getOrdered() {
		return ordered;
	}

	public void setOrdered(int order) {
		this.ordered = order;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public GatewayRoute clone(GatewayRoute entity) {
		super.clone(entity);
		entity.setUri(getUri());
		entity.setPath(getPath());
		entity.setStripPrefix(getStripPrefix());
		entity.setOrdered(getOrdered());
		entity.setMethod(getMethod());
		entity.setGroup(getGroup());
		entity.setWeight(getWeight());
		return entity;
	}

}
