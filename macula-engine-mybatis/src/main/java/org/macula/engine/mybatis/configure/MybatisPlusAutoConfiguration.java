package org.macula.engine.mybatis.configure;

import java.util.List;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.macula.engine.assistant.provider.PrincipalAssistantProvider;
import org.macula.engine.mybatis.customize.MybatisAuditMetaObjectHandler;
import org.macula.engine.mybatis.customize.SnowflakeIdentifierGenerator;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@Configuration
@EnableTransactionManagement
class MybatisPlusAutoConfiguration {

	private Long MAX_LIMIT = 1000L;

	@PostConstruct
	void postConstruct() {
		log.debug("[Macula] |- [Engine Mybatis Plus] Auto Configure.");
	}

	/**
	* 防止 修改与删除时对全表进行操作
	* @return {@link MybatisPlusInterceptor}
	*/
	@Bean
	@ConditionalOnMissingBean
	MybatisPlusInterceptor mybatisPlusInterceptor(List<InnerInterceptor> innerInterceptors) {
		MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
		if (innerInterceptors != null) {
			innerInterceptors.forEach(innerInterceptor -> mybatisPlusInterceptor.addInnerInterceptor(innerInterceptor));
		}
		log.debug("[Macula] |- Bean [Mybatis Plus Interceptor]  with interceptos [{}] Auto Configure.", innerInterceptors);
		return mybatisPlusInterceptor;
	}

	@Bean
	@ConditionalOnMissingBean
	PaginationInnerInterceptor paginationInnerInterceptor() {
		PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
		paginationInnerInterceptor.setMaxLimit(MAX_LIMIT);
		log.debug("[Macula] |- Bean [PaginationInnerInterceptor] Auto Configure.");
		return paginationInnerInterceptor;
	}

	@Bean
	@ConditionalOnMissingBean
	BlockAttackInnerInterceptor blockAttackInnerInterceptor() {
		BlockAttackInnerInterceptor blockAttackInnerInterceptor = new BlockAttackInnerInterceptor();
		log.debug("[Macula] |- Bean [Block Attack Inner Interceptor] Auto Configure.");
		return blockAttackInnerInterceptor;
	}

	@Bean
	@ConditionalOnMissingBean
	OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
		OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor = new OptimisticLockerInnerInterceptor();
		log.debug("[Macula] |- Bean [OptimisticLockerInnerInterceptor] Auto Configure.");
		return optimisticLockerInnerInterceptor;
	}

	@Bean
	MybatisPlusPropertiesCustomizer mybatisPlusPropertiesCustomizer(SnowflakeIdentifierGenerator snowflakeIdentifierGenerator) {
		log.debug("[Macula] |- Bean [MybatisPlusPropertiesCustomizer] setIdentifierGenerator [SnowflakeIdentifierGenerator] Auto Configure.");
		return plusProperties -> {
			plusProperties.getGlobalConfig().setIdentifierGenerator(snowflakeIdentifierGenerator);
		};
	}

	@Bean
	@ConditionalOnMissingBean(IdentifierGenerator.class)
	SnowflakeIdentifierGenerator identifierGenerator() {
		SnowflakeIdentifierGenerator snowflakeIdentifierGenerator = new SnowflakeIdentifierGenerator();
		log.debug("[Macula] |- Bean [SnowflakeIdentifierGenerator] Auto Configure.");
		return snowflakeIdentifierGenerator;
	}

	@Bean
	@ConditionalOnBean(MybatisPlusProperties.class)
	@ConditionalOnMissingBean(MetaObjectHandler.class)
	MetaObjectHandler metaObjectHandler(MybatisPlusProperties mybatisProperties, ObjectProvider<PrincipalAssistantProvider> principalObjectProvider) {
		log.debug("[Macula] |- Bean [MybatisAuditMetaObjectHandler] Auto Configure.");
		MetaObjectHandler metaObjectHandler = new MybatisAuditMetaObjectHandler(mybatisProperties.getConfigurationProperties(),
				principalObjectProvider.getIfAvailable());
		return metaObjectHandler;
	}
}
