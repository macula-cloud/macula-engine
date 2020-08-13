package org.macula.cloud.api.protocol;

import java.util.ArrayList;
import java.util.List;

import org.macula.cloud.api.context.CloudApplicationContext;
import org.macula.cloud.api.exception.FormBindException;
import org.macula.cloud.api.exception.MaculaException;
import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * <b>Response</b> 返回数据结构的基类
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
public class Response {

	/** 是否成功标识 */
	private boolean success;

	/** 系统级错误代码 */
	private String errorCode;

	/** 系统级错误信息 */
	private String errorMessage;

	/** 业务级错误代码 */
	private String exceptionCode;

	/** 业务级错误信息 */
	private String exceptionMessage;

	/** 异常详细信息 */
	private String exceptionStack;

	/** 服务端重定向信息 */
	private String redirection;

	/** 校验结果信息 */
	private List<FieldError> validateErrors;

	public Response(MaculaException exception) {
		this.success = false;
		this.errorCode = exception.getParentCode();
		this.exceptionCode = exception.getMessage();
		this.exceptionMessage = exception.getLocalizedMessage();

		this.exceptionStack = exception.getFullStackMessage();

		if (exception instanceof FormBindException) {
			List<FieldError> fieldErrors = ((FormBindException) exception).getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				this.addValidateError(fieldError);
			}
		}
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		if (!StringUtils.isEmpty(errorMessage)) {
			return CloudApplicationContext.getMessage(errorMessage);
		}
		if (!StringUtils.isEmpty(errorCode)) {
			return CloudApplicationContext.getMessage(errorCode);
		}
		return errorMessage;
	}

	/**
	 * @return the exceptionMessage
	 */
	public String getExceptionMessage() {
		if (!StringUtils.isEmpty(exceptionMessage)) {
			return CloudApplicationContext.getMessage(exceptionMessage);
		}
		if (!StringUtils.isEmpty(exceptionCode)) {
			return CloudApplicationContext.getMessage(exceptionCode);
		}
		return exceptionMessage;
	}

	public final void addValidateError(FieldError fieldError) {
		if (this.validateErrors == null) {
			validateErrors = new ArrayList<FieldError>();
		}
		validateErrors.add(fieldError);
	}

	public void appendExceptionStack(String exception) {
		if (exceptionStack == null) {
			exceptionStack = exception;
		} else {
			exceptionStack += '\n' + exception;
		}
	}

}
