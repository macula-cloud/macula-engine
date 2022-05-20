package org.macula.engine.j2cache;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = J2CacheApplication.class)
@Slf4j
public class J2CacheTestCases {

	@Autowired
	private MessageCacheService service;

	@Test
	public void testGetMessage() {
		String value = String.valueOf(System.nanoTime());
		for (int i = 0; i < 10; i++) {
			log.info("GetMessage {}: [{}]", i, service.getMessage(value));
		}
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}

}
