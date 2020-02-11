package org.macula.cloud.sdk.exception;


import org.macula.cloud.sdk.utils.CloudConstants;

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
		return CloudConstants.MACULA_DATA_JPA_ERROR_CODE;
	}

}
