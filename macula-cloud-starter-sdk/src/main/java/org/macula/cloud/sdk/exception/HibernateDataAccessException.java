package org.macula.cloud.sdk.exception;


import org.macula.cloud.sdk.utils.CloudConstants;

/**
 * <p>
 * <b>HibernateDataAccessException</b> 所有HibernateException会被转为该异常
 * </p>
 */
public class HibernateDataAccessException extends MaculaException {

	private static final long serialVersionUID = 1L;

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
		return CloudConstants.MACULA_DATA_HIBERNATE_ERROR_CODE;
	}

}
