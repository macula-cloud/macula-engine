package org.macula.cloud.sdk;

import org.macula.cloud.sdk.lock.annotation.ZookeeperLock;
import org.springframework.stereotype.Component;

@Component
public class TestZookeeperLock {

	@ZookeeperLock("'lock-01'")
	public void testZookeeperLock() {
		System.out.println("Do in lock-01");
	}
}
