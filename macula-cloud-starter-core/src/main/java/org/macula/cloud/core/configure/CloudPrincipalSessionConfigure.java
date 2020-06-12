package org.macula.cloud.core.configure;

import java.util.List;

import org.macula.cloud.core.event.CloudKafkaMessagePush;
import org.macula.cloud.core.event.MissingKafkaConfigureEventListener;
import org.macula.cloud.core.principal.SubjectPrincipalCreatedListener;
import org.macula.cloud.core.principal.SubjectPrincipalSessionStorage;
import org.macula.cloud.core.principal.SubjectPropertyResolver;
import org.macula.cloud.core.session.SessionCreatedListener;
import org.macula.cloud.core.session.SessionDestroyListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(name = "org.springframework.security.core.userdetails.User")
public class CloudPrincipalSessionConfigure {

	@Bean
	public SubjectPrincipalSessionStorage subjectPrincipalSessionStorage() {
		return new SubjectPrincipalSessionStorage();
	}

	@Bean
	public SessionCreatedListener userSessionCreatedListener(SubjectPrincipalSessionStorage storage) {
		return new SessionCreatedListener(storage);
	}

	@Bean
	public SessionDestroyListener userSessionDestroyListener(SubjectPrincipalSessionStorage storage) {
		return new SessionDestroyListener(storage);
	}

	@Bean
	public SubjectPrincipalCreatedListener subjectPrincipalCreatedListener(SubjectPrincipalSessionStorage storage,
			List<SubjectPropertyResolver> userPropertyResolvers) {
		return new SubjectPrincipalCreatedListener(storage, userPropertyResolvers);
	}

	@Bean
	@ConditionalOnMissingBean(CloudKafkaMessagePush.class)
	public MissingKafkaConfigureEventListener missingConfigureEventListener() {
		return new MissingKafkaConfigureEventListener();
	}
}
