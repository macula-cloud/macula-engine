package org.macula.cloud.core.utils;

/**
 * <p>
 * <b>ExceptionUtils</b> 异常助手
 * </p>
 * 
 */
public final class ExceptionUtils extends org.apache.commons.lang3.exception.ExceptionUtils {

	@SuppressWarnings("unchecked")
	public static <T> T getRecursionCauseException(Throwable e, Class<T> clzz) {
		if (e == null) {
			return null;
		}

		if (clzz.isAssignableFrom(e.getClass())) {
			return (T) e;
		}
		return getRecursionCauseException(e.getCause(), clzz);
	}
}
