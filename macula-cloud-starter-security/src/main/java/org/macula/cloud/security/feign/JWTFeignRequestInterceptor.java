package org.macula.cloud.security.feign;

import org.macula.cloud.core.principal.SubjectPrincipal;
import org.macula.cloud.core.utils.SecurityUtils;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTFeignRequestInterceptor implements RequestInterceptor {

	/**
	 * The name of the token.
	 */
	public static final String BEARER = "Bearer";

	/**
	 * The name of the header.
	 */
	public static final String AUTHORIZATION = "Authorization";

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private Signer signer;

	public JWTFeignRequestInterceptor(String key) {
		signer = new MacSigner(key);
	}

	@Override
	public void apply(RequestTemplate template) {
		String token = getAuthenticationHeaderToken();
		if (token == null) {
			token = generatePrincipalToken();
		}
		if (token != null) {
			template.header(AUTHORIZATION, token);
		}
	}

	protected String getAuthenticationHeaderToken() {
		RequestAttributes requestAttrs = RequestContextHolder.getRequestAttributes();
		if (requestAttrs != null && requestAttrs instanceof ServletRequestAttributes) {
			return ((ServletRequestAttributes) requestAttrs).getRequest().getHeader(AUTHORIZATION);
		}
		return null;
	}

	protected String generatePrincipalToken() {
		SubjectPrincipal principal = SecurityUtils.getLoginedPrincipal();
		if (principal != null) {
			try {
				return String.format("%s %s", BEARER, JwtHelper.encode(OBJECT_MAPPER.writeValueAsString(principal), signer).getEncoded());
			} catch (JsonProcessingException e) {
				log.error("Generate logined principal jwt token failed {}", e);
			}
		}
		return null;
	}
}
