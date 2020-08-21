package org.macula.cloud.security.feign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

import feign.MethodMetadata;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("deprecation")
@Slf4j
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {

	private OAuth2ClientContext oAuth2ClientContext;
	private List<? extends OAuth2ProtectedResourceDetails> resources;
	private Map<String, org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor> interceptorMap = new HashMap<String, org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor>();

	public OAuth2FeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext, List<? extends OAuth2ProtectedResourceDetails> resources) {
		this.oAuth2ClientContext = oAuth2ClientContext;
		this.resources = resources;
	}

	@Override
	public void apply(RequestTemplate template) {
		if (template.headers().containsKey(org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor.AUTHORIZATION)) {
			return;
		}
		MethodMetadata methodMeta = template.methodMetadata();
		OAuth2Api oauth2ApiAnnotation = methodMeta.method().getDeclaringClass().getAnnotation(OAuth2Api.class);
		if (oauth2ApiAnnotation != null) {
			String clientId = oauth2ApiAnnotation.clientId();
			org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor interceptor = getOAuth2FeignRequestInterceptor(
					clientId);
			try {
				interceptor.apply(template);
			} catch (Exception ex) {
				log.error("Apply OAuth2FeignRequest error: ", ex);
			}
		}
	}

	protected org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor getOAuth2FeignRequestInterceptor(String clientId) {
		org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor interceptor = interceptorMap.get(clientId);
		if (interceptor == null) {
			for (OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails : resources) {
				if (StringUtils.equals(clientId, oAuth2ProtectedResourceDetails.getClientId())) {
					interceptor = new org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor(oAuth2ClientContext,
							oAuth2ProtectedResourceDetails);
					interceptorMap.put(clientId, interceptor);
				}
			}
		}
		return interceptor;
	}
}
