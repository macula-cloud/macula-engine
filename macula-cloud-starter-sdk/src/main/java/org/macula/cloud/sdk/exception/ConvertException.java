package org.macula.cloud.sdk.exception;


import org.macula.cloud.sdk.utils.CloudConstants;

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
		return CloudConstants.MACULA_CORE_CONVERT_CODE;
	}

}
