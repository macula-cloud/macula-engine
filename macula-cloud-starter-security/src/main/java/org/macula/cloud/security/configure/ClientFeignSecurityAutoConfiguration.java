package org.macula.cloud.security.configure;

import java.util.List;

import org.macula.cloud.core.configure.CoreConfigurationProperties;
import org.macula.cloud.security.feign.JWTFeignRequestInterceptor;
import org.macula.cloud.security.feign.OAuth2FeignRequestInterceptor;
import org.macula.cloud.security.feign.OpenApiFeignInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import feign.RequestInterceptor;

public class ClientFeignSecurityAutoConfiguration {

	@Bean
	@ConditionalOnProperty("security.oauth2.client.client-id")
	@ConfigurationProperties("security.oauth2.client")
	private ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
		return new ClientCredentialsResourceDetails();
	}

	@Bean
	@Order(100)
	public RequestInterceptor jwtFeignRequestInterceptor(CoreConfigurationProperties properties) {
		return new JWTFeignRequestInterceptor(properties.getSecurity().getJwtKey());
	}

	@ConditionalOnBean(ClientCredentialsResourceDetails.class)
	@Bean
	@Order(200)
	public RequestInterceptor oauth2FeignRequestInterceptor(List<? extends OAuth2ProtectedResourceDetails> clientDetails) {
		return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientDetails);
	}

	@Bean
	@Order(300)
	public RequestInterceptor openApiFeignInterceptor(CoreConfigurationProperties properties) {
		return new OpenApiFeignInterceptor(properties.getSecurity().getOpenApi());
	}

	@ConditionalOnBean(ClientCredentialsResourceDetails.class)
	@Bean
	public OAuth2RestTemplate clientCredentialsRestTemplate(ClientCredentialsResourceDetails clientDetails) {
		return new OAuth2RestTemplate(clientDetails);
	}
}
