package org.macula.cloud.core.configure;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

public class EnableJpaRepositoryCondition extends SpringBootCondition {

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Object[] enablers = context.getBeanFactory().getBeanNamesForAnnotation(EnableJpaRepositories.class);
		ConditionMessage.Builder message = ConditionMessage.forCondition("@EnableJpaRepositories Condition");
		if (enablers != null && enablers.length > 0) {
			return ConditionOutcome.match(message.found("@EnableJpaRepositories annotation on Application").items(enablers));
		}

		return ConditionOutcome.noMatch(message.didNotFind("@EnableJpaRepositories annotation on Application").atAll());
	}
}
