package org.macula.cloud.sdk.principal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.core.Ordered;

/**
 * 用户属性解析
 */
public interface SubjectPropertyResolver extends Ordered {

	/**
	 * 获取额外的用户角色
	 */
	default Set<String> getCustomAuthories(SubjectPrincipal subjectPrincipal) {
		return new HashSet<String>();
	}

	/**
	 * 获取额外的用户属性
	 */
	default Map<String, Serializable> getCustomProperties(SubjectPrincipal subjectPrincipal) {
		return new HashMap<String, Serializable>();
	}

}
