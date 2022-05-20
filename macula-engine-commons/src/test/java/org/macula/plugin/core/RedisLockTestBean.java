package org.macula.plugin.core;

import lombok.extern.slf4j.Slf4j;
import org.macula.engine.lock.annotation.RedisLock;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisLockTestBean {

	@RedisLock("'lock-01'")
	public String testRedisLock() {
		log.info("[Macula] |- COMMON === Do in lock-01");
		return "Return Lock Method";
	}
}
