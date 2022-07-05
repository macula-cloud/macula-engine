package org.macula.engine.security.subject;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.macula.engine.assistant.constants.Versions;

/**
 * Subject Attribute
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubjectAttribute implements Serializable {

	private static final long serialVersionUID = Versions.serialVersion;

	private String name;

	private Serializable value;
}
