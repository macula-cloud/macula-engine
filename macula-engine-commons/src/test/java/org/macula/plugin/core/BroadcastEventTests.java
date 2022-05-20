package org.macula.plugin.core;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.macula.engine.commons.event.MaculaRemoteEvent;
import org.macula.engine.j2cache.utils.J2CacheUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootTest(classes = CommonsApplication.class)
public class BroadcastEventTests {

	@Autowired
	private RedisLockTestBean redisLockBean;

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void broadcastEventTest() {

		MaculaRemoteEvent broadcastEvent = MaculaRemoteEvent.every("EveryNodeEvent Payload 001");
		MaculaRemoteEvent instanceEvent = MaculaRemoteEvent.once("OnceNodeEvent Payload 002");

		J2CacheUtils.set(J2CacheUtils.CACHE_REGION, "my-broadcast-event", broadcastEvent);
		J2CacheUtils.set(J2CacheUtils.CACHE_REGION, "my-instance-event", instanceEvent);

		MaculaRemoteEvent broadcastValue = J2CacheUtils.get(J2CacheUtils.CACHE_REGION, "my-broadcast-event");
		MaculaRemoteEvent instanceValue = J2CacheUtils.get(J2CacheUtils.CACHE_REGION, "my-instance-event");
		log.info("====二级缓存==== broadcastEvent: " + broadcastValue + " instanceEvent: " + instanceValue);

		String result = redisLockBean.testRedisLock();
		log.info("===  TestLock get Result: " + result);
		applicationContext.publishEvent(broadcastEvent);
	}

}
