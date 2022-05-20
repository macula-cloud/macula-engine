package org.macula.cloud.security.access;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.macula.cloud.cache.utils.J2CacheUtils;
import org.macula.cloud.core.configure.model.SecurityProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityAccessMetadataSource implements FilterInvocationSecurityMetadataSource {

	private static final String URL_ROLE_MAPPING_CACHE = "url_role_mapping";
	private Map<RequestMatcher, Collection<ConfigAttribute>> metadata = new HashMap<>();

	private List<RequestMatcher> ignoreMetadata = new ArrayList<RequestMatcher>();
	private SecurityProperties securityConfig;

	public SecurityAccessMetadataSource(SecurityProperties security) {
		this.securityConfig = security;
		for (String path : this.securityConfig.getIgnorePaths()) {
			ignoreMetadata.add(new AntPathRequestMatcher(path));
		}
		for (String path : this.securityConfig.getPublicPaths()) {
			ignoreMetadata.add(new AntPathRequestMatcher(path));
		}
	}

	@Scheduled(fixedRate = 3600 * 1000, initialDelay = 1000)
	public void loadUrlRoleMappings() {
		log.info("Scheduled execute loadUrlRoleMappings");
		Map<RequestMatcher, Collection<ConfigAttribute>> loadMetadata = new HashMap<>();
		Map<String, Set<String>> urlRoleMapping = J2CacheUtils.get(J2CacheUtils.CACHE_REGION, URL_ROLE_MAPPING_CACHE);
		if (urlRoleMapping != null) {
			for (Map.Entry<String, Set<String>> entry : urlRoleMapping.entrySet()) {
				Set<String> roleCodes = entry.getValue();
				Collection<ConfigAttribute> configs = CollectionUtils.collect(roleCodes, input -> new SecurityConfig(input));
				loadMetadata.put(new AntPathRequestMatcher(entry.getKey()), configs);
			}
		}
		metadata = loadMetadata;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();

		for (RequestMatcher requestMatcher : ignoreMetadata) {
			if (requestMatcher.matches(request)) {
				return Collections.emptyList();
			}
		}

		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : metadata.entrySet()) {
			RequestMatcher matcher = entry.getKey();
			if (matcher.matches(request)) {
				return entry.getValue();
			}
		}
		return CollectionUtils.collect(securityConfig.getResourceAuthorities(), input -> new SecurityConfig(input));
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return Collections.emptyList();
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
