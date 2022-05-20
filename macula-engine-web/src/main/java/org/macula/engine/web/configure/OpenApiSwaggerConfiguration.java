package org.macula.engine.web.configure;

import javax.annotation.PostConstruct;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;
import org.macula.engine.assistant.constants.BaseConstants;
import org.macula.engine.web.provider.OpenApiServersResolver;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> OpenAPI Swagger Configuration </p>
 */
@SecuritySchemes({
		@SecurityScheme(name = BaseConstants.OPEN_API_SECURITY_SCHEME_BEARER_NAME, type = SecuritySchemeType.OAUTH2, bearerFormat = "JWT", scheme = "bearer",
				// Support flows
				flows = @OAuthFlows(
						// Password Flow
						password = @OAuthFlow(authorizationUrl = "${macula.endpoint.authorization-uri}",
								// TokenUrl
								tokenUrl = "${macula.endpoint.access-token-uri}",
								// RefreshUrl
								refreshUrl = "${macula.endpoint.access-token-uri}",
								// Scopes
								scopes = @OAuthScope(name = "all")),
						// Client Credentials Flow
						clientCredentials = @OAuthFlow(authorizationUrl = "${macula.endpoint.authorization-uri}",
								// TokenUrl
								tokenUrl = "${macula.endpoint.access-token-uri}",
								// RefreshUrl
								refreshUrl = "${macula.endpoint.access-token-uri}",
								// Scopes
								scopes = @OAuthScope(name = "all"))
				//						,authorizationCode = @OAuthFlow(
				//								authorizationUrl = "${macula.platform.endpoint.user-authorization-uri}", 
				//								tokenUrl = "${macula.platform.endpoint.access-token-uri}", 
				//								refreshUrl = "${macula.platform.endpoint.access-token-uri}", 
				//								scopes = @OAuthScope(name = "all"))
				)), })
@Slf4j
@Configuration
public class OpenApiSwaggerConfiguration {

	@PostConstruct
	public void postConstruct() {
		log.debug("[Macula] |- Plugin [Engine Web OpenApi Swagger v3] Auto Configure.");
	}

	@Bean
	@ConditionalOnMissingBean
	public OpenAPI createOpenApi(OpenApiServersResolver openApiServersResolver) {
		log.debug("[Macula] |- Bean [OpenAPI] Auto Configure.");
		return new OpenAPI().servers(openApiServersResolver.getServers())
				.info(new Info().title("Macula Cloud").description("Macula Cloud Platform Applications").version("Swagger V3")
						.license(new License().name("MIT").url("http://opensource.org/licenses/MIT")))
				.externalDocs(new ExternalDocumentation().description("Macula Cloud Documentation").url(" https://github.com/macula-cloud"));
	}
}
