package org.macula.engine.web.configure;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Import({
		JacksonUtilsConfiguration.class,
		OpenApiSwaggerConfiguration.class,
		WebContextConfiguration.class })
@Configuration
public class WebAutoConfiguration {

	@PostConstruct
	public void postConstruct() {
		log.debug("[Macula] |- Plugin [Engine Web] Auto Configure.");
	}

}
