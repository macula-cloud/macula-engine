package org.macula.cloud.core.configure;

import org.macula.cloud.api.context.CloudApplicationContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(CoreConfigurationProperties.class)
@Import({
		CloudJpaConfigure.class,
		CloudRedisLockConfigure.class,
		CloudPrincipalSessionConfigure.class })
@ComponentScan({
		"org.macula.cloud.core",
		"org.macula.cloud.security" })
public class CloudConfiguration {

	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	public CloudApplicationContext cloudApplicationContext() {
		CloudApplicationContext applicationContext = new CloudApplicationContext();
		applicationContext.setApplicationContext(this.applicationContext);
		return applicationContext;
	}
}
