package org.macula.engine.assistant.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.macula.engine.assistant.constants.Versions;

/**
 * <p>
 * <b>MaculaException</b> MACULA框架的异常基类
 * </p>
 */
public class MaculaException extends RuntimeException {

	private static final long serialVersionUID = Versions.serialVersion;

	public MaculaException(String message) {
		super(message);
	}

	public MaculaException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getFullStackMessage() {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		this.printStackTrace(pw);
		return sw.getBuffer().toString();
	}

}
