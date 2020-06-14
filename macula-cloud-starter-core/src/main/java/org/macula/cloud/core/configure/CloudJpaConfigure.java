package org.macula.cloud.core.configure;

import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.macula.cloud.core.utils.SecurityUtils;
import org.macula.cloud.core.utils.SystemUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ConditionalOnProperty(prefix = "spring.data.jpa.repositories", name = "enabled", havingValue = "true", matchIfMissing = true)
public class CloudJpaConfigure {

	@Bean(name = "macula-cloud-datasource")
	@ConditionalOnProperty("spring.datasource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "macula-cloud-jpaProperties")
	@ConditionalOnProperty("spring.jpa")
	@ConfigurationProperties(prefix = "spring.jpa")
	public JpaProperties jpaProperties() {
		return new JpaProperties();
	}

	@ConditionalOnBean(name = { "macula-cloud-datasource", "macula-cloud-jpaProperties" })
	@Bean(name = "macula-cloud-entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(dataSource()).packages("org.macula.cloud.**.domain").properties(jpaProperties().getProperties())
				.persistenceUnit("macula-cloud").build();
	}

	@ConditionalOnBean(name = "macula-cloud-entityManagerFactory")
	@Bean(name = "macula-cloud-transactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("macula-cloud-entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	@Configuration
	@ConditionalOnProperty(prefix = "spring.data.jpa.repositories", name = "enabled", havingValue = "true", matchIfMissing = true)
	static class CloudJpaAuditingConfiguration {
		@Bean
		public AuditorAware<String> buildAuditorAwareImpl() {
			return new AuditorAware<String>() {

				@Override
				public Optional<String> getCurrentAuditor() {
					return Optional.of(SecurityUtils.getSubjectPrincipal().getName());
				}
			};
		}

		@Bean
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
