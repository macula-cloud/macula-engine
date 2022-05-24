package org.macula.engine.assistant.protocol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.macula.engine.assistant.constants.Versions;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldOption implements Serializable {

	private static final long serialVersionUID = Versions.serialVersion;

	public static final String ID_PARAM = "id";
	public static final String CODE_PARAM = "code";
	public static final String LABEL_PARAM = "label";

	private Object id;
	private Object code;
	private String label;

	private final Map<String, Object> extra = new HashMap<String, Object>();

	public FieldOption(Object code, String label) {
		this(null, code, label);
	}

}
