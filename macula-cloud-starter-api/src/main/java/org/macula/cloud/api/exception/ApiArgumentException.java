package org.macula.cloud.api.exception;

import org.macula.cloud.api.protocol.ApiConstants;

/**
 * <p>
 * <b>MaculaArgumentException</b> 是输入参数异常.
 * </p>
 * 
 */
public class ApiArgumentException extends MaculaException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param ex
	 */
	public ApiArgumentException(IllegalArgumentException ex) {
		super(ex.getMessage(), ex);
	}

	/**
	 * @param code
	 * @param args
	 */
	public ApiArgumentException(String code, Object... args) {
		super(code, args);
	}

	@Override
	public String getParentCode() {
		return ApiConstants.MACULA_CORE_ARGS_CODE;
	}

}
