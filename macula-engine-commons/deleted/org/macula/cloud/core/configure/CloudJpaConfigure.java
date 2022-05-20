package org.macula.cloud.core.configure;

import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import org.macula.cloud.core.utils.SystemUtils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;

@Configuration
@Conditional(EnableJpaRepositoryCondition.class)
public class CloudJpaConfigure {

	@Configuration
	static class CloudJpaAuditingConfiguration {
		@Bean("auditorAware")
		public AuditorAware<String> buildAuditorAwareImpl() {
			return new AuditorAware<String>() {

				@Override
				public Optional<String> getCurrentAuditor() {
					return Optional.of("*SYSADMIN*"
					// TODO  update 
					// SecurityUtils.getSubjectPrincipal().getName()
					);
				}
			};
		}

		@Bean("dateTimeProvider")
		public DateTimeProvider buildDateTimeProvider() {
			return new DateTimeProvider() {

				@Override
				public Optional<TemporalAccessor> getNow() {
					return Optional.of(SystemUtils.getCurrentInstant());
				}

			};
		}
	}

}
