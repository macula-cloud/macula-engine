package org.macula.cloud.sdk.protocol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FieldOption implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String ID = "id";
	public static final String CODE = "code";
	public static final String LABEL = "label";

	private Object id;
	private Object code;
	private String label;

	private final Map<String, Object> extra = new HashMap<String, Object>();

	public FieldOption(Object code, String label) {
		this(null, code, label);

	}

	public FieldOption(Object id, Object code, String label) {
		this.id = id;
		this.code = code;
		this.label = label;
	}

}
