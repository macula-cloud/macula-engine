package org.macula.cloud.data.value;

import org.macula.cloud.core.principal.SubjectPrincipal;
import org.springframework.core.Ordered;

/**
 * <p> <b>ValueEntryResolver</b> 是对执行上下文涉及到的数据的解析接口. </p>
 */
public interface ValueEntryResolver extends Ordered, Comparable<ValueEntryResolver> {

	/**
	 * 是否能解析.
	 */
	boolean support(String key);

	/**
	 * 解析指定的值.
	 */
	ValueEntry resolve(String attribute, SubjectPrincipal userContext);

}
