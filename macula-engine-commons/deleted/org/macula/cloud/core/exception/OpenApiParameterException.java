package org.macula.cloud.core.exception;

import org.macula.cloud.api.exception.MaculaException;
import org.macula.cloud.core.constant.GlobalConstant;

public class OpenApiParameterException extends MaculaException {

	private static final long serialVersionUID = Versions.serialVersion;

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
