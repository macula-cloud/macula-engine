package org.macula.cloud.sdk.exception;

import org.macula.cloud.sdk.constant.GlobalConstant;

public class OpenApiParameterException extends MaculaException {

	private static final long serialVersionUID = 1L;

	public OpenApiParameterException(String message) {
		super(message);
	}

	public OpenApiParameterException(String message, Throwable ex) {
		super(message, ex);
	}

	@Override
	public String getParentCode() {
		return GlobalConstant.MACULA_CLOUD_PARAM_CODE;
	}

}
