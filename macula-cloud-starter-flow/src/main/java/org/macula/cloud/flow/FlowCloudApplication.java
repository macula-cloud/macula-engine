package org.macula.cloud.flow;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;

@SpringCloudApplication
@ImportResource(locations = { "classpath:uflo-console-context.xml" })
@EnableFeignClients
public class FlowCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowCloudApplication.class, args);
	}
}
