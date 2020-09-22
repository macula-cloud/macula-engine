package org.macula.cloud.core.exception;

import org.macula.cloud.api.exception.MaculaException;
import org.macula.cloud.api.protocol.ApiConstants;

/**
 * <p>
 * <b>ServiceException</b> 服务层默认异常
 * </p>
 *
 */
public class ServiceException extends MaculaException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param ex
	 */
	public ServiceException(String message, Throwable ex) {
		super(message, ex);
	}

	@Override
	public String getParentCode() {
		return ApiConstants.MACULA_CORE_SERVICE_CODE;
	}

}
