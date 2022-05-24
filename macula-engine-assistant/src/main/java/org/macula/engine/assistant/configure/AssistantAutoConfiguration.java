package org.macula.engine.assistant.configure;

import javax.annotation.PostConstruct;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.macula.engine.assistant.query.ConversionUtils;
import org.macula.engine.assistant.support.ApplicationId;
import org.macula.engine.assistant.support.ApplicationResolver;
import org.macula.engine.assistant.utils.StringUtils;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.Environment;

@Slf4j
@Configuration
public class AssistantAutoConfiguration {

	private static final String APPLICATION_GROUP_DEFAULT = "MACULA";

	@PostConstruct
	public void postConstruct() {
		log.debug("[Macula] |- Plugin [Engine Assistant] Auto Configure.");
	}

	@Bean
	public ApplicationId currentApplicationId(Environment env) {
		String applicationGroup = ApplicationResolver.getApplicationGroup(env);
		String applicationName = ApplicationResolver.getApplicationName(env);
		String applicationInstance = ApplicationResolver.getApplicationInstance(env);

		ApplicationId.setCurrent(new ApplicationId(getGroupId(applicationGroup), applicationName, getInstanceId(applicationInstance)));

		log.debug("[Macula] |- Bean [ApplicationId] Auto Configure.");
		return ApplicationId.current();
	}

	@Bean
	public ConversionUtils conversionUtils(ObjectProvider<ConversionService> conversionService) {
		log.debug("[Macula] |- Bean [ConversionUtils] Auto Configure.");
		return ConversionUtils.of(conversionService.getIfAvailable());
	}

	@Bean
	public SpringUtil springUtil() {
		log.debug("[Macula] |- Bean [SpringUtil] Auto Configure.");
		return new SpringUtil();
	}

	private String getGroupId(String group) {
		return group == null ? APPLICATION_GROUP_DEFAULT : group;
	}

	private String getInstanceId(String instance) {
		return instance != null ? instance : StringUtils.getHostAddress();
	}
}
