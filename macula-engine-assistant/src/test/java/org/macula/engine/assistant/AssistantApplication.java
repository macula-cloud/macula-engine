package org.macula.engine.assistant;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AssistantApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(AssistantApplication.class, args);
	}
}