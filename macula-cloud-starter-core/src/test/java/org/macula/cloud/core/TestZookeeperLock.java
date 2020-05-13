package org.macula.cloud.core;

import org.macula.cloud.core.lock.annotation.ZookeeperLock;
import org.springframework.stereotype.Component;

@Component
public class TestZookeeperLock {

	@ZookeeperLock("'lock-01'")
	public void testZookeeperLock() {
		System.out.println("Do in lock-01");
	}
}
