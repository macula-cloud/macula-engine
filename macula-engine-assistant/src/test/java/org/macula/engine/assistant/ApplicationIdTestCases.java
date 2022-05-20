package org.macula.engine.assistant;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.macula.engine.assistant.support.ApplicationId;

import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = AssistantApplication.class)
public class ApplicationIdTestCases {

	@Test
	public void applicationIdEqualsTest() {
		log.info("Run applicationIdEqualsTest");
		ApplicationId actual = new ApplicationId("macula-cloud", "macula-engine-assistant", "A001");
		Assert.assertEquals(ApplicationId.current().getInstanceKey(), actual.getInstanceKey());
	}

}
