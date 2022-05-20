package org.macula.engine.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaMessageChannelApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaMessageChannelApplication.class, args);
	}
}
