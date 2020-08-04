package org.macula.cloud.br.configure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ JdbcTemplate.class, PlatformTransactionManager.class })
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
@EnableConfigurationProperties(DataSourceProperties.class)
public class ChoerodonConfiguration {

	//	@Bean
	//	DataSourceTransactionManager transactionManager(DataSource dataSource,
	//			ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
	//		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
	//		transactionManagerCustomizers.ifAvailable((customizers) -> customizers.customize(transactionManager));
	//		return transactionManager;
	//	}

}
