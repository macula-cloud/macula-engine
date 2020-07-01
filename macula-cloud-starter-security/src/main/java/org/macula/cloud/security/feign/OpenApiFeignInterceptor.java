package org.macula.cloud.security.feign;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.macula.cloud.core.utils.HttpRequestUtils;

import feign.MethodMetadata;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class OpenApiFeignInterceptor implements RequestInterceptor {

	private final Map<String, String> openApiConfig;

	public OpenApiFeignInterceptor(Map<String, String> openApiConfig) {
		this.openApiConfig = openApiConfig == null ? new HashMap<>() : openApiConfig;
	}

	@Override
	public void apply(RequestTemplate template) {
		MethodMetadata methodMeta = template.methodMetadata();
		OpenApi openApiAnnotation = methodMeta.method().getDeclaringClass().getAnnotation(OpenApi.class);
		if (openApiAnnotation != null) {
			String clientId = openApiAnnotation.clientId();
			String clientSecret = openApiConfig.get(clientId);
			Map<String, Collection<String>> queries = template.queries();
			Map<String, String> params = FeignOpenApiHelper.createOpenApiSignMap(clientId, clientSecret, queries);
			params.forEach((k, v) -> {
				template.query(k, v);
			});
			template.header(HttpRequestUtils.AJAX_REQUEST_HEADER, HttpRequestUtils.API_REQUEST_VALUE);
		}
	}

}
