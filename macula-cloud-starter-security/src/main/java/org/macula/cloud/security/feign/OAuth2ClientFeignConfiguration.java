package org.macula.cloud.security.feign;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import feign.RequestInterceptor;

public class OAuth2ClientFeignConfiguration {

	@Bean
	@ConfigurationProperties("security.oauth2.client")
	private ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
		return new ClientCredentialsResourceDetails();
	}

	@Bean
	@RefreshScope
	public RequestInterceptor oauth2FeignRequestInterceptor(ClientCredentialsResourceDetails clientDetails) {
		return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientDetails);
	}

	@Bean
	@RefreshScope
	public OAuth2RestTemplate clientCredentialsRestTemplate(ClientCredentialsResourceDetails clientDetails) {
		return new OAuth2RestTemplate(clientDetails);
	}
}
