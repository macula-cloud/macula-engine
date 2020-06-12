package org.macula.cloud.log;

import org.macula.cloud.log.annotation.ServiceInvokeProxy;
import org.springframework.stereotype.Component;

@Component
public class MyServiceInvokeLog {

	@ServiceInvokeProxy
	public String getName(String name) {
		return "Hello:" + name;
	}

}
