package org.macula.plugin.core;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {
		HibernateJpaAutoConfiguration.class,
		DataSourceAutoConfiguration.class })
public class CommonsApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(CommonsApplication.class, args);
	}
}
