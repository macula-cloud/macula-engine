package org.macula.engine.authority.configure;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AuthorityAutoConfiguration {

	@PostConstruct
	public void postConstruct() {
		log.debug("[Macula] |- Plugin [Engine Authority] Auto Configure.");
	}
}
