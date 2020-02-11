package org.macula.cloud.sdk.exception;


import java.util.Locale;

import org.macula.cloud.sdk.context.CloudApplicationContext;

/**
 * <p>
 * <b>I18nException</b> 可以国际化消息的异常
 * </p>
 */
public abstract class I18nException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Object[] args = null;

	public I18nException() {
		super();
	}

	public I18nException(Throwable cause) {
		super(cause);
	}

	public I18nException(String message) {
		super(message);
	}

	public I18nException(String message, Throwable cause) {
		super(message, cause);
	}

	public I18nException(String message, Object[] args) {
		super(message);
		this.args = args == null ? null : args.clone();
	}

	public I18nException(String message, Object[] args, Throwable cause) {
		super(message, cause);
		this.args = args == null ? null : args.clone();
	}

	@Override
	public String getLocalizedMessage() {
		return CloudApplicationContext.getMessage(getMessage(), args);
	}

	public String getLocalizedMessage(Locale locale) {
		return CloudApplicationContext.getMessage(getMessage(), args, getMessage(), locale);
	}
}
