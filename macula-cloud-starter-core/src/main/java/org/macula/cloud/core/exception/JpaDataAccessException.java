package org.macula.cloud.core.exception;


import org.macula.cloud.api.exception.MaculaException;
import org.macula.cloud.api.protocol.ApiConstants;

/**
 * <p>
 * <b>JpaDataAccessException</b> 所有PersistenceException异常被转为该异常
 * </p>
 */
public class JpaDataAccessException extends MaculaException {

	private static final long serialVersionUID = 1L;

	public JpaDataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public JpaDataAccessException(String message, Object[] args) {
		super(message, args);
	}

	public JpaDataAccessException(String message, Object[] args, Throwable cause) {
		super(message, args, cause);
	}

	@Override
	public String getParentCode() {
		return ApiConstants.MACULA_DATA_JPA_ERROR_CODE;
	}

}
