//package org.macula.cloud.core.oauth2;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedAuthoritiesExtractor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.util.CollectionUtils;
//
//public class SubjectAuthoritiesExtractor extends FixedAuthoritiesExtractor {
//	private static final String AUTHORITIES = "authorities";
//	private String defaultRole;
//
//	public SubjectAuthoritiesExtractor(String defaultRole) {
//		this.defaultRole = defaultRole;
//	}
//
//	@Override
//	public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
//		Object theMap = getAuthorities(map);
//		if (theMap != null) {
//			map.put(AUTHORITIES, theMap);
//		}
//		List<GrantedAuthority> authorities = super.extractAuthorities(map);
//		if (CollectionUtils.isEmpty(authorities) && StringUtils.isNotEmpty(defaultRole)) {
//			authorities = Collections.singletonList(new SimpleGrantedAuthority(defaultRole));
//		}
//		return authorities;
//	}
//
//	Object getAuthorities(Map<String, Object> map) {
//		Object o = map.get(AUTHORITIES);
//		if (o instanceof Map<?, ?>) {
//			@SuppressWarnings("unchecked")
//			Map<String, Object> o2 = (Map<String, Object>) o;
//			if (o2.containsKey(AUTHORITIES)) {
//				return getAuthorities(o2);
//			} else {
//				return o2.values();
//			}
//		}
//		return o;
//	}
//}
