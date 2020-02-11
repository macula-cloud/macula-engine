package org.macula.cloud.security.access;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

public class EnableResourceServerCondition extends SpringBootCondition {

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Object[] enablers = context.getBeanFactory().getBeanNamesForAnnotation(EnableResourceServer.class);
		ConditionMessage.Builder message = ConditionMessage.forCondition("@EnableResourceServer Condition");
		if (enablers != null && enablers.length > 0) {
			return ConditionOutcome
					.match(message.found("@EnableResourceServer annotation on Application").items(enablers));
		}

		return ConditionOutcome.noMatch(message.didNotFind("@EnableResourceServer annotation on Application").atAll());
	}
}
