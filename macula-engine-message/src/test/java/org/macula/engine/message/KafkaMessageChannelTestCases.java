package org.macula.engine.message;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.macula.engine.commons.event.MaculaRemoteEvent;
import org.macula.engine.message.User.Bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(classes = KafkaMessageChannelApplication.class)
public class KafkaMessageChannelTestCases {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void testMaculaEvent001() throws InterruptedException {
		for (int i = 0; i < 5; i++) {
			MaculaRemoteEvent broadcastEvent = MaculaRemoteEvent.every(
					new User("name" + i, "password" + i, i + 10, new Bank("ChinaBank", BigDecimal.valueOf(53.0d))));
			applicationContext.publishEvent(broadcastEvent);
			Thread.sleep(5 * 1000);
		}
	}

}
