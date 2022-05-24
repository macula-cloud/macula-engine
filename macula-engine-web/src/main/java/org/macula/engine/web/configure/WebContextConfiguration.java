package org.macula.engine.web.configure;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.macula.engine.web.context.WebApplicationContext;
import org.macula.engine.web.properties.EndpointProperties;
import org.macula.engine.web.properties.PlatformProperties;
import org.macula.engine.web.provider.DefaultOpenApiServersResolver;
import org.macula.engine.web.provider.OpenApiServersResolver;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>Web Application Context Configuration </p>
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({
		PlatformProperties.class,
		EndpointProperties.class })
@Import({
		RestTemplateConfiguration.class })
public class WebContextConfiguration {

	@PostConstruct
	public void postConstruct() {
		log.debug("[Macula] |- SDK [Engine Web Rest] Auto Configure.");
	}

	@Bean
	@ConditionalOnMissingBean
	public WebApplicationContext webApplicationContext(ApplicationContext applicationContext, PlatformProperties platformProperties,
			EndpointProperties endpointProperties, ServerProperties serverProperties) {
		WebApplicationContext contextHolder = new WebApplicationContext(applicationContext, platformProperties, endpointProperties,
				serverProperties);
		log.trace("[Macula] |- Bean [Macula Application  Context Holder] Auto Configure.");
		return contextHolder;
	}

	@Bean
	@ConditionalOnMissingBean
	public OpenApiServersResolver openApiServersResolver(WebApplicationContext webApplicationContext) {
		DefaultOpenApiServersResolver defaultOpenApiServersResolver = new DefaultOpenApiServersResolver(webApplicationContext);
		log.trace("[Macula] |- Bean [Open Api Server Resolver] Auto Configure.");
		return defaultOpenApiServersResolver;
	}

}
