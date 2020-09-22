package org.macula.cloud.log.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { LogApplicationTest.class })
@EnableJpaRepositories
@EntityScan
@ComponentScan
public class LogApplicationTest {

	@Autowired
	private ServiceTest myservice;

	@Test
	public void testGetName() {
		String result = myservice.getName("Wilson");
		System.out.println(result);
	}

}
