package org.macula.engine.commons.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.macula.engine.assistant.constants.Versions;

/**
 * 
 * <p>Tracing Log</p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
@ToString(callSuper = true)
@Getter
@Setter
public abstract class TraceLog extends ApplicationAsset {

	private static final long serialVersionUID = Versions.serialVersion;

	@Column(name = "TRACE_ID")
	private String traceId;

	public TraceLog clone(TraceLog entity) {
		super.clone(entity);
		entity.setTraceId(getTraceId());
		return entity;
	}

}
