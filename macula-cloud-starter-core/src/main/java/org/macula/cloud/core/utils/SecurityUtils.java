package org.macula.cloud.core.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.macula.cloud.core.configure.model.SecurityProperties;
import org.macula.cloud.core.principal.SubjectPrincipal;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class SecurityUtils {

	private static Authentication anonymousAuthentication = new AnonymousAuthenticationToken("key", "anonymous",
			AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));

	private static SubjectPrincipal anonymousPrincipal = new SubjectPrincipal(anonymousAuthentication.getName(), "",
			anonymousAuthentication.getAuthorities());

	private final static ObjectMapper objectMapper = new ObjectMapper();

	public static SubjectPrincipal getSubjectPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			if (authentication.getPrincipal() instanceof SubjectPrincipal) {
				return (SubjectPrincipal) authentication.getPrincipal();
			}
			Object details = authentication.getDetails();
			if (details instanceof OAuth2AuthenticationDetails) {
				Object decodedDetails = ((OAuth2AuthenticationDetails) details).getDecodedDetails();
				if (decodedDetails instanceof SubjectPrincipal) {
					return (SubjectPrincipal) decodedDetails;
				}
			}
		}
		return anonymousPrincipal;
	}

	public static SubjectPrincipal getLoginedPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return getLoginedPrincipal(authentication);
	}

	public static SubjectPrincipal getLoginedPrincipal(Authentication authentication) {
		if (authentication != null) {
			if (authentication.getPrincipal() instanceof SubjectPrincipal) {
				return (SubjectPrincipal) authentication.getPrincipal();
			}
			Object details = authentication.getDetails();
			if (details instanceof OAuth2AuthenticationDetails) {
				Object decodedDetails = ((OAuth2AuthenticationDetails) details).getDecodedDetails();
				if (decodedDetails instanceof SubjectPrincipal) {
					return (SubjectPrincipal) decodedDetails;
				}
			}
		}
		return null;
	}

	public static String[] getResourceAuthorities(SecurityProperties securityProperties, String springAppName) {
		Set<String> hasAuthories = new HashSet<String>();
		String authories = securityProperties.getNeedAuthories();
		if (StringUtils.hasLength(authories)) {
			hasAuthories.addAll(StringUtils.commaDelimitedListToSet(authories));
		}
		if (securityProperties.isIncludeDefault()) {
			hasAuthories.add("macula-cloud-gateway"); // for user
			if (StringUtils.hasLength(springAppName)) {
				hasAuthories.add(springAppName); // for oauth2 client
			}
		}

		hasAuthories.forEach(t -> t.toLowerCase());
		return StringUtils.toStringArray(hasAuthories);
	}

	public static Authentication cast(SubjectPrincipal principal) {
		if (principal == null) {
			return anonymousAuthentication;
		}
		principal.eraseCredentials();
		return new PreAuthenticatedAuthenticationToken(principal, principal.getCredential(), principal.getAuthorities());
	}

	public static String convertPrincipal(SubjectPrincipal principal, Signer jwtSigner) {
		if (principal != null) {
			try {
				String token = objectMapper.writeValueAsString(principal);
				String jwt = "Bearer " + JwtHelper.encode(token, jwtSigner).getEncoded();
				return jwt;
			} catch (JsonProcessingException e) {
				log.error("convertPrincipal error: ", e);
			}
		}
		return null;
	}

	public static Map<String, Object> convertMap(String jwt, SignatureVerifier jwtSigner) {
		try {
			String claims = JwtHelper.decodeAndVerify(jwt, jwtSigner).getClaims();
			return objectMapper.readValue(claims, new TypeReference<HashMap<String, Object>>() {
			});
		} catch (JsonProcessingException e) {
			log.error("convertPrincipal error: ", e);
		}
		return null;
	}

	public static boolean isJwtToken(String token) {
		return token != null && token.indexOf(".") > 0;
	}
}
