package org.macula.cloud.api.exception;

/**
 * <p>
 * <b>OpenApiException</b> Open API 调用异常
 * </p>
 */
public class OpenApiException extends MaculaException {
	private static final long serialVersionUID = 1L;

	public OpenApiException(String message) {
		super(message);
	}

	public OpenApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public OpenApiException(String message, Object[] args) {
		super(message, args);
	}

	public OpenApiException(String message, Object[] args, Throwable cause) {
		super(message, args, cause);
	}

	@Override
	public String getParentCode() {
		return "OPENAPI";
	}
}
