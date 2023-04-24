package org.macula.engine.tracing.autoconfigure;

import jakarta.annotation.PostConstruct;
import org.macula.engine.tracing.mvc.TraceIdResponseAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class TracingAutoConfiguration {

	private static final Logger log = LoggerFactory.getLogger(TracingAutoConfiguration.class);

	@PostConstruct
	public void postConstruct() {
		log.info("[Macula] |- Starter [Engine Tracing Starter] Auto Configure.");
	}

	@Bean
	TraceIdResponseAdvice traceIdResponseAdvice() {
		return new TraceIdResponseAdvice();
	}

}
