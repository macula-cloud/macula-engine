package org.macula.cloud.security.token;

import org.macula.cloud.core.oauth2.SubjectPrincipalExtractor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerTokenServicesConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(ResourceServerTokenServicesConfiguration.class)
public class SubjectPrincipalExtractorConfiguration {

	@Bean
	public SubjectPrincipalExtractor subjectPrincipalExtractor() {
		return new SubjectPrincipalExtractor();
	}

}
