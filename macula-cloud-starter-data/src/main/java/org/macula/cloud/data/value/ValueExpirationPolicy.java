package org.macula.cloud.data.value;

/**
 * <p> <b>ValueExpirationPolicy</b> 是失效策略. </p>
 */
public interface ValueExpirationPolicy {

	/**
	 * 检测是否失效的策略.
	 */
	boolean isExpired(ValueEntry valueEntry);

}
