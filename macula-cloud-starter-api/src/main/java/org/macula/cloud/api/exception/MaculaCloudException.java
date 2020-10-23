package org.macula.cloud.api.exception;

public class MaculaCloudException extends MaculaException {

	private static final long serialVersionUID = 1L;

	public MaculaCloudException(String message) {
		super(message);
	}

	public MaculaCloudException(String message, Exception exception) {
		super(message, exception);
	}

	@Override
	public String getParentCode() {
		return "CLOUD";
	}

}
