package org.macula.engine.security.subject;

import java.io.Serializable;

import org.macula.engine.assistant.constants.Versions;

/**
 * 创建SubjectPrincipal成功事件。
 * 
 * 在Macula Cloud微服务体系中，应该采用InstanceProcessEvent将此事件发出，
 * 事件的发出方应该是Auth服务，事件响应方可以是所有的微服务。
 */
public class SubjectPrincipalCreatedEvent implements Serializable {

	private static final long serialVersionUID = Versions.serialVersion;

	private final String subjectId;

	public SubjectPrincipalCreatedEvent(String subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * @return the subjectId
	 */
	public String getSubjectId() {
		return this.subjectId;
	}

}
