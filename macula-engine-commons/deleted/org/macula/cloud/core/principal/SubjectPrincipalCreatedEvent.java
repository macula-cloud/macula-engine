package org.macula.cloud.core.principal;

import org.macula.cloud.core.event.MaculaEvent;

/**
 * 创建SubjectPrincipal成功事件。
 * 
 * 在Macula Cloud微服务体系中，应该采用InstanceProcessEvent将此事件发出，
 * 事件的发出方应该是Auth服务，事件响应方可以是所有的微服务。
 */
public class SubjectPrincipalCreatedEvent extends MaculaEvent<String> {

	private static final long serialVersionUID = 1L;

	public SubjectPrincipalCreatedEvent(String guid) {
		super(guid);
	}

}
