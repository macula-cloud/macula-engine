package org.macula.cloud.core;

import org.macula.cloud.core.annotation.RedisLock;
import org.springframework.stereotype.Component;

@Component
public class TestRedisLock {

	@RedisLock("'lock-01'")
	public String testRedisLock() {
		System.out.println("Do in lock-01");
		return "Return Lock Method";
	}
}
