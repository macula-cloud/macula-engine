package org.macula.plugin.security.configure;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SecurityAutoConfiguration {

	@PostConstruct
	public void postConstruct() {
		log.debug("[Macula] |- Plugin [Security  Plugin] Auto Configure.");
	}

	//	@Bean
	//	@ConditionalOnMissingBean
	//	public PageJacksonModule pageJacksonModule() {
	//		return new PageJacksonModule();
	//	}
	//
	//	@Bean
	//	@ConditionalOnMissingBean
	//	public Module sortModule() {
	//		return new SortJacksonModule();
	//	}
	//
	//	@Bean
	//	@ConditionalOnProperty("security.oauth2.client.client-id")
	//	@ConfigurationProperties("security.oauth2.client")
	//	private ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
	//		return new ClientCredentialsResourceDetails();
	//	}
	//
	//	@Bean
	//	@Order(100)
	//	public RequestInterceptor jwtFeignRequestInterceptor(CoreConfigurationProperties properties) {
	//		return new JWTFeignRequestInterceptor(properties.getSecurity().getJwtKey());
	//	}
	//
	//	@ConditionalOnBean(ClientCredentialsResourceDetails.class)
	//	@Bean
	//	@Order(200)
	//	public RequestInterceptor oauth2FeignRequestInterceptor(List<? extends OAuth2ProtectedResourceDetails> clientDetails) {
	//		return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientDetails);
	//	}
	//
	//	@Bean
	//	@Order(300)
	//	public RequestInterceptor openApiFeignInterceptor(CoreConfigurationProperties properties) {
	//		return new OpenApiFeignInterceptor(properties.getSecurity().getOpenApi());
	//	}
	//
	//	@ConditionalOnBean(ClientCredentialsResourceDetails.class)
	//	@Bean
	//	public OAuth2RestTemplate clientCredentialsRestTemplate(ClientCredentialsResourceDetails clientDetails) {
	//		return new OAuth2RestTemplate(clientDetails);
	//	}
}
