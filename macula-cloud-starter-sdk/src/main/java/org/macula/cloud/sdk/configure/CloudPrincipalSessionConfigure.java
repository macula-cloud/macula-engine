package org.macula.cloud.sdk.configure;

import java.util.List;

import org.macula.cloud.sdk.event.CloudKafkaMessagePush;
import org.macula.cloud.sdk.event.MissingKafkaConfigureEventListener;
import org.macula.cloud.sdk.principal.SubjectPrincipalCreatedListener;
import org.macula.cloud.sdk.principal.SubjectPrincipalSessionStorage;
import org.macula.cloud.sdk.principal.SubjectPropertyResolver;
import org.macula.cloud.sdk.session.SessionCreatedListener;
import org.macula.cloud.sdk.session.SessionDestroyListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
