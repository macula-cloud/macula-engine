package org.macula.cloud.api.exception;

import org.macula.cloud.api.protocol.ApiConstants;

/**
 * <p>
 * <b>ConvertException</b> 类型转换异常
 * </p>
 */
public class ConvertException extends MaculaException {

	private static final long serialVersionUID = 1L;

	public ConvertException(Throwable e) {
		super("Convert Error", e);
	}

	@Override
	public String getParentCode() {
		return ApiConstants.MACULA_CORE_CONVERT_CODE;
	}

}
