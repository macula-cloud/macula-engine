package org.macula.cloud.core.exception;

import org.macula.cloud.api.exception.MaculaException;
import org.macula.cloud.api.protocol.ApiConstants;

/**
 * <p>
 * <b>HibernateDataAccessException</b> 所有HibernateException会被转为该异常
 * </p>
 */
public class HibernateDataAccessException extends MaculaException {

	private static final long serialVersionUID = Versions.serialVersion;

	public HibernateDataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public HibernateDataAccessException(String message, Object[] args) {
		super(message, args);
	}

	public HibernateDataAccessException(String message, Object[] args, Throwable cause) {
		super(message, args, cause);
	}

	@Override
	public String getParentCode() {
		return ApiConstants.MACULA_DATA_HIBERNATE_ERROR_CODE;
	}

}
