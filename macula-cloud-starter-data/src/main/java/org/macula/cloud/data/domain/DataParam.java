package org.macula.cloud.data.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.macula.cloud.api.protocol.DataType;
import org.macula.cloud.core.domain.ApplicationAsset;
import org.macula.cloud.data.validation.Length2;
import org.macula.cloud.data.value.scope.ValueScope;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
@ToString(callSuper = true)
public class DataParam extends ApplicationAsset implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "TYPE", nullable = false, length = 10)
	@NotNull
	@Length2(min = 1, max = 10)
	private String type;
	@Column(name = "VALUE", columnDefinition = "CLOB")
	private String value;
	@Column(name = "VALUE_SCOPE", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private ValueScope valueScope;
	@Column(name = "IS_ENABLED", nullable = false)
	private boolean enabled;
	@Column(name = "PARAM_CLZ", nullable = false, length = 50)
	@Enumerated(EnumType.STRING)
	@NotNull
	private DataType dataType;
	@Column(name = "COMMENTS", length = 255)
	@Length2(min = 0, max = 255)
	private String comments;
	@ManyToOne(targetEntity = DataSource.class, optional = true)
	private DataSource dataSource;
	@Column(name = "ORDERED", nullable = false, length = 2)
	private int ordered;

	public static DataParam createDataParam(Long id) {
		if (id == null) {
			return null;
		}
		DataParam dataParam = new DataParam();
		dataParam.setId(id);
		return dataParam;
	}

	public Long getParentId() {
		return getParent() == null ? null : getParent().getId();
	}

	public void setParentId(Long id) {
		setParent(createDataParam(id));
	}

	public String getParentName() {
		return getParent() == null ? null : getParent().getName();
	}

}
