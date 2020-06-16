package org.macula.cloud.security.token;

import org.macula.cloud.core.configure.CoreConfigurationProperties;
import org.macula.cloud.core.oauth2.SubjectAuthoritiesExtractor;
import org.macula.cloud.core.oauth2.SubjectPrincipalExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerTokenServicesConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(ResourceServerTokenServicesConfiguration.class)
public class SubjectPrincipalExtractorConfiguration {

	@Autowired
	private CoreConfigurationProperties properties;

	@Bean
	public AuthoritiesExtractor subjectAuthoritiesExtractor() {
		return new SubjectAuthoritiesExtractor(properties.getSecurity().getDefaultRole());
	}

	@Bean
	public PrincipalExtractor subjectPrincipalExtractor(AuthoritiesExtractor authoritiesExtractor) {
		return new SubjectPrincipalExtractor(authoritiesExtractor);
	}

}
