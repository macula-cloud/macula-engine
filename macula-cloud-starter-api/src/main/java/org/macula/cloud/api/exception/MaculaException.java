package org.macula.cloud.api.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <p>
 * <b>MaculaException</b> MACULA框架的异常基类
 * </p>
 */
public abstract class MaculaException extends I18nException {

	private static final long serialVersionUID = 1L;

	public MaculaException(String message) {
		super(message);
	}

	public MaculaException(String message, Throwable cause) {
		super(message, cause);
	}

	public MaculaException(String message, Object[] args) {
		super(message, args);
	}

	public MaculaException(String message, Object[] args, Throwable cause) {
		super(message, args, cause);
	}

	public String getFullStackMessage() {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		this.printStackTrace(pw);
		return sw.getBuffer().toString();
	}

	/**
	 * 父错误码
	 */
	abstract public String getParentCode();

}
