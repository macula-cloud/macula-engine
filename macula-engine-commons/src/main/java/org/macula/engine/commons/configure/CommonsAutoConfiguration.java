package org.macula.engine.commons.configure;

import jakarta.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CommonsAutoConfiguration {

	@PostConstruct
	public void postConstruct() {
		log.debug("[Macula] |- Plugin [Engine Commons] Auto Configure.");
	}
}
