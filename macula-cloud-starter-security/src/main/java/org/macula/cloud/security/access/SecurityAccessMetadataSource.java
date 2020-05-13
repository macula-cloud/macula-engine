package org.macula.cloud.security.access;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.macula.cloud.core.utils.J2CacheUtils;
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

	@Scheduled(fixedRate = 3600 * 1000, initialDelay = 1000)
	public void loadUrlRoleMappings() {
		log.info("scheduled execute loadUrlRoleMappings");
		Map<RequestMatcher, Collection<ConfigAttribute>> loadMetadata = new HashMap<>();
		Map<String, Set<String>> urlRoleMapping = J2CacheUtils.get(J2CacheUtils.CACHE_REGION, URL_ROLE_MAPPING_CACHE);
		if (urlRoleMapping != null) {
			for (Map.Entry<String, Set<String>> entry : urlRoleMapping.entrySet()) {
				Set<String> roleCodes = entry.getValue();
				Collection<ConfigAttribute> configs = CollectionUtils.collect(roleCodes.iterator(),
						input -> new SecurityConfig(input));
				loadMetadata.put(new AntPathRequestMatcher(entry.getKey()), configs);
			}
		}
		metadata = loadMetadata;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : metadata.entrySet()) {
			RequestMatcher matcher = entry.getKey();
			if (matcher.matches(request)) {
				return entry.getValue();
			}
		}
		return Collections.emptyList();
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
