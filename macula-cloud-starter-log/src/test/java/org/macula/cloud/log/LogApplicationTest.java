package org.macula.cloud.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class })
public class LogApplicationTest {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(LogApplicationTest.class, args);
		MyServiceInvokeLog myservice = applicationContext.getBean(MyServiceInvokeLog.class);
		String result = myservice.getName("Wilson");
		System.out.println(result);
	}

}
