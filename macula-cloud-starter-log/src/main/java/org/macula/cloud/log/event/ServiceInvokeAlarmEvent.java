package org.macula.cloud.log.event;

import org.macula.cloud.core.domain.ServiceInvokeLog;
import org.macula.cloud.core.event.MaculaEvent;

public class ServiceInvokeAlarmEvent extends MaculaEvent<ServiceInvokeLog> {

	private static final long serialVersionUID = 1L;

	public ServiceInvokeAlarmEvent(ServiceInvokeLog source) {
		super(source);
	}

}
