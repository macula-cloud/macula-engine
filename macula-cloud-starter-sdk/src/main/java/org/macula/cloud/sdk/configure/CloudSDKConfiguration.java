package org.macula.cloud.sdk.configure;

import org.macula.cloud.sdk.application.ApplicationId;
import org.macula.cloud.sdk.context.CloudApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(SDKConfigurationProperties.class)
@Import({ CloudEventKafkaConfigure.class, CloudCacheConfigure.class, CloudJpaConfigure.class, CloudLockConfigure.class,
		CloudPrincipalSessionConfigure.class })
@ComponentScan({ "org.macula.cloud.sdk", "org.macula.cloud.security" })
public class CloudSDKConfiguration {

	@Autowired
	private SDKConfigurationProperties sdkProperties;

	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	public ApplicationId applicationId() {
		if (ApplicationId.current() == null) {
			ApplicationId.setCurrent(sdkProperties.getApplication().create());
		}
		return ApplicationId.current();
	}

	@Bean
	public CloudApplicationContext cloudApplicationContext() {
		CloudApplicationContext applicationContext = new CloudApplicationContext();
		applicationContext.setApplicationContext(this.applicationContext);
		return applicationContext;
	}
}
