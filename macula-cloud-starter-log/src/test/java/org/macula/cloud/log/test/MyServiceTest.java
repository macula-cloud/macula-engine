package org.macula.cloud.log.test;

import org.macula.cloud.log.annotation.ServiceInvokeProxy;
import org.springframework.stereotype.Component;

@Component
public class MyServiceTest implements ServiceTest {

	@ServiceInvokeProxy
	public String getName(String name) {
		return "Hello:" + getInternalName(name);
	}

	public String getInternalName(String name) {
		return "InternalName:-> " + name;
	}

}
