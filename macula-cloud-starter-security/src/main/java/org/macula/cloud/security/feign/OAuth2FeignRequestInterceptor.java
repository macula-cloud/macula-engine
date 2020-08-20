package org.macula.cloud.security.feign;

import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

import feign.MethodMetadata;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("deprecation")
@Slf4j
public class OAuth2FeignRequestInterceptor extends org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor {

	public OAuth2FeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails resource) {
		super(oAuth2ClientContext, resource);
	}

	@Override
	public void apply(RequestTemplate template) {
		MethodMetadata methodMeta = template.methodMetadata();
		OAuth2Api oauth2ApiAnnotation = methodMeta.method().getDeclaringClass().getAnnotation(OAuth2Api.class);
		if (oauth2ApiAnnotation != null) {
			String clientId = oauth2ApiAnnotation.clientId();
			// TODO load OAuth2ProtectedResourceDetails from clientId
			if (template.headers().containsKey(AUTHORIZATION)) {
				return;
			}
			try {
				super.apply(template);
			} catch (Exception ex) {
				log.error("Apply OAuth2FeignRequest error: ", ex);
			}
		}
	}

}
