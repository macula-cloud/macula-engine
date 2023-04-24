package org.macula.cloud.security.access;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.macula.cloud.core.configure.CoreConfigurationProperties;
import org.macula.cloud.core.configure.model.SecurityProperties;
import org.macula.cloud.core.oauth2.SubjectPrincipalUserInfoTokenServices;
import org.macula.cloud.core.servlet.RequestAccessLogFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityAccessAutoConfiguration {

	@Autowired
	private CoreConfigurationProperties properties;

	@Bean
	public RequestAccessLogFilter requestRecorderFilter() {
		List<String> ignorePaths = new ArrayList<String>();
		ignorePaths.addAll(Arrays.asList(properties.getSecurity().getIgnorePaths()));
		ignorePaths.addAll(Arrays.asList(properties.getSecurity().getPublicUrls()));
		return new RequestAccessLogFilter(ignorePaths);
	}

	@Bean
	@Primary
	@ConditionalOnMissingBean(AuthorizationEndpoint.class)
	@Conditional(EnableResourceServerCondition.class)
	public UserInfoTokenServices oauth2UserInfoTokenServices(OAuth2ClientContext oauth2ClientContext, OAuth2ProtectedResourceDetails client,
			PrincipalExtractor principalExtractor, AuthoritiesExtractor authoritesExtractor, ResourceServerProperties resource) {
		SubjectPrincipalUserInfoTokenServices tokenServices = new SubjectPrincipalUserInfoTokenServices(resource.getUserInfoUri(),
				resource.getClientId(), principalExtractor, authoritesExtractor, new MacSigner(properties.getSecurity().getJwtKey()));
		tokenServices.setRestTemplate(new OAuth2RestTemplate(client, oauth2ClientContext));
		return tokenServices;
	}

	@Bean
	public FilterInvocationSecurityMetadataSource securityAccessMetadataSource() {
		SecurityAccessMetadataSource securityAccessMetadataSource = new SecurityAccessMetadataSource(properties.getSecurity());
		return securityAccessMetadataSource;
	}

	@Bean
	public FilterSecurityInterceptor securityAccessDecisionInterceptor() {
		FilterSecurityInterceptor securityInterceptor = new FilterSecurityInterceptor();
		securityInterceptor.setAccessDecisionManager(new SecurityAccessDecisionManager());
		securityInterceptor.setSecurityMetadataSource(this.securityAccessMetadataSource());
		return securityInterceptor;
	}

	@Configuration
	@Conditional(EnableResourceServerCondition.class)
	@NoArgsConstructor
	private static class ResourceServerSecurityAccessConfiguration extends ResourceServerConfigurerAdapter {

		@Autowired
		private CoreConfigurationProperties properties;

		@Autowired(required = false)
		private ResourceServerTokenServices tokenServies;

		@Autowired(required = false)
		private List<SecurityAccessPluginConfigure> pluginConfigures;

		@Autowired
		private RequestAccessLogFilter requestAccessLogFilter;

		@Autowired
		private FilterSecurityInterceptor filterSecurityInterceptor;

		protected void configurePluginConfigures(HttpSecurity http) throws Exception {
			if (CollectionUtils.isNotEmpty(pluginConfigures)) {
				pluginConfigures.forEach(pluginConfigure -> {
					try {
						pluginConfigure.configureResourceServer(http);
					} catch (Exception e) {
						log.error("Config error: ", e);
					}
				});
			}
		}

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			resources.resourceId("default");
			if (tokenServies != null) {
				resources.tokenServices(tokenServies);
			}
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.csrf().disable();
			SecurityProperties securityProperties = properties.getSecurity();
			http.requestMatchers().antMatchers(securityProperties.getResourcePaths());
			http.authorizeRequests().anyRequest().authenticated();
			http.addFilterBefore(filterSecurityInterceptor, FilterSecurityInterceptor.class).addFilterAfter(requestAccessLogFilter,
					SecurityContextPersistenceFilter.class);

			configurePluginConfigures(http);
			log.info("Security Access Control is enabled on Resource Server Application");
		}
	}

	@Configuration
	@Conditional(EnableWebSecurityCondition.class)
	@NoArgsConstructor
	@Order(200)
	private static class WebSecurityAccessConfiguration extends WebSecurityConfigurerAdapter {

		@Autowired
		private CoreConfigurationProperties properties;

		@Autowired(required = false)
		private AuthenticationFailureHandler authenticationFailureHandler;

		@Autowired(required = false)
		private AuthenticationSuccessHandler authenticationSuccessHandler;

		@Autowired(required = false)
		private LogoutSuccessHandler logoutSuccessHandler;

		@Autowired(required = false)
		private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> detailsSource;

		@Autowired(required = false)
		private ResourceServerTokenServices tokenServices;

		@Autowired(required = false)
		private OAuth2ClientContext oauth2ClientContext;

		@Autowired(required = false)
		private OAuth2ProtectedResourceDetails client;

		@Autowired(required = false)
		private OAuth2AuthenticationManager oauth2AuthenticationManager;

		@Autowired(required = false)
		private ResourceServerProperties resource;

		@Autowired(required = false)
		private OAuth2ClientContextFilter oauth2ClientContextFilter;

		@Autowired
		private RequestAccessLogFilter requestAccessLogFilter;

		@Autowired(required = false)
		private List<SecurityAccessPluginConfigure> pluginConfigures;

		@Autowired
		private FilterSecurityInterceptor filterSecurityInterceptor;

		protected void initPluginConfigures() {
			if (CollectionUtils.isNotEmpty(pluginConfigures)) {
				pluginConfigures.forEach(
						pluginConfigure -> pluginConfigure.init(authenticationSuccessHandler, authenticationFailureHandler, logoutSuccessHandler));
			}
		}

		protected void configurePluginConfigures(HttpSecurity http) throws Exception {
			if (CollectionUtils.isNotEmpty(pluginConfigures)) {
				pluginConfigures.forEach(pluginConfigure -> {
					try {
						pluginConfigure.configureAccess(http);
					} catch (Exception e) {
						log.error("Plugin configure error: ", e);
					}
				});
			}
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			SecurityProperties securityProperties = properties.getSecurity();
			web.ignoring().antMatchers(securityProperties.getIgnorePaths());
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			SecurityProperties securityProperties = properties.getSecurity();

			initPluginConfigures();

			http.csrf().disable();

			FormLoginConfigurer<HttpSecurity> formLoginConfig = http.httpBasic().disable().formLogin();
			formLoginConfig.loginPage(properties.getSecurity().getLoginPath()).loginProcessingUrl("/login");

			if (detailsSource != null) {
				formLoginConfig.authenticationDetailsSource(detailsSource);
			}

			if (authenticationFailureHandler != null) {
				formLoginConfig.failureHandler(authenticationFailureHandler);
			}
			if (authenticationSuccessHandler != null) {
				formLoginConfig.successHandler(authenticationSuccessHandler);
			}

			LogoutConfigurer<HttpSecurity> logoutConfig = http.logout().deleteCookies(OAuth2AccessToken.ACCESS_TOKEN).invalidateHttpSession(true);
			if (logoutSuccessHandler != null) {
				logoutConfig.logoutSuccessHandler(logoutSuccessHandler);
			}

			http.authorizeRequests().anyRequest().authenticated();

			http.addFilterBefore(filterSecurityInterceptor, FilterSecurityInterceptor.class);
			http.addFilterBefore(requestAccessLogFilter, SecurityContextPersistenceFilter.class);

			if (oauth2ClientContextFilter != null) {
				http.addFilterBefore(oauth2ClientContextFilter, BasicAuthenticationFilter.class);
			}

			if (securityProperties.isOauth2Login()) {
				http.addFilterBefore(createOAuth2ClientAuthenticationFilter(securityProperties.getOauth2Callback()), BasicAuthenticationFilter.class);
				if (tokenServices != null && resource != null) {
					http.addFilterBefore(createOAuth2AuthenticationFilter(), OAuth2ClientAuthenticationProcessingFilter.class);
				}
			}

			HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
			requestCache.setRequestMatcher(new NegatedRequestMatcher(new AntPathRequestMatcher("/error")));
			http.setSharedObject(RequestCache.class, requestCache);

			http.exceptionHandling()
					.authenticationEntryPoint(new RedirectLoginUrlAuthenticationEntryPoint(properties.getSecurity().getLoginPath(), requestCache));

			configurePluginConfigures(http);

			log.info("Security Access Control is enabled on Web Application");
		}

		protected AuthenticationManager getOAuth2AuthenticationManager() {
			if (oauth2AuthenticationManager == null) {
				oauth2AuthenticationManager = new OAuth2AuthenticationManager();
				oauth2AuthenticationManager.setResourceId(resource.getResourceId());
			}
			oauth2AuthenticationManager.setTokenServices(tokenServices);
			return oauth2AuthenticationManager;
		}

		protected Filter createOAuth2AuthenticationFilter() {
			OAuth2AuthenticationProcessingFilter oauth2AuthenticationFilter = new OAuth2AuthenticationProcessingFilter();
			oauth2AuthenticationFilter.setAuthenticationManager(getOAuth2AuthenticationManager());
			oauth2AuthenticationFilter.afterPropertiesSet();
			return oauth2AuthenticationFilter;
		}

		protected Filter createOAuth2ClientAuthenticationFilter(String path) {
			OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
			filter.setAuthenticationManager(getOAuth2AuthenticationManager());
			OAuth2RestTemplate template = new OAuth2RestTemplate(client, oauth2ClientContext);
			filter.setRestTemplate(template);
			if (authenticationSuccessHandler != null) {
				filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
			}
			if (tokenServices != null) {
				filter.setTokenServices(tokenServices);
			}
			filter.afterPropertiesSet();
			return filter;
		}

		@Bean
		@Override
		protected AuthenticationManager authenticationManager() throws Exception {
			return super.authenticationManager();
		}

	}

}
