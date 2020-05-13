package org.macula.cloud.core.exception;

import org.macula.cloud.core.context.CloudApplicationContext;
import org.macula.cloud.core.protocol.FieldError;
import org.macula.cloud.core.utils.CloudConstants;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class FormBindException extends MaculaException {

	private static final long serialVersionUID = 1L;

	private final BindingResult[] bindingResults;

	private final List<FieldError> fieldErrors = new ArrayList<FieldError>();

	private final StringBuilder fullMessage = new StringBuilder();

	public FormBindException(BindingResult... bindingResults) {
		super("form.bind.exception");
		this.bindingResults = bindingResults;
		for (BindingResult result : bindingResults) {
			if (result.hasErrors()) {
				for (ObjectError objectError : result.getAllErrors()) {
					if (objectError instanceof org.springframework.validation.FieldError) {
						// 字段级的错误，ObjectName+.+字段名返回错误信息
						if (!StringUtils.isEmpty(objectError.getObjectName())) {
							FieldError fieldError = new FieldError(
									objectError.getObjectName() + "."
											+ ((org.springframework.validation.FieldError) objectError).getField(),
									CloudApplicationContext.getMessage(objectError));
							fieldErrors.add(fieldError);
						} else {
							FieldError fieldError = new FieldError(
									((org.springframework.validation.FieldError) objectError).getField(),
									CloudApplicationContext.getMessage(objectError));
							fieldErrors.add(fieldError);
						}
					} else {
						// 全局错误
						fullMessage.append(CloudApplicationContext.getMessage(objectError) + "\n");
					}
				}
			}
		}
	}

	public BindingResult[] getBindingResults() {
		// 这里不克隆，减少内存操作
		return this.bindingResults;
	}

	@Override
	public String getParentCode() {
		return CloudConstants.MACULA_CORE_VALID_CODE;
	}

	@Override
	public String getFullStackMessage() {
		return fullMessage.toString();
	}

	/**
	 * @return the fieldErrors
	 */
	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	/**
	 * @return the fullMessage
	 */
	public StringBuilder getFullMessage() {
		return fullMessage;
	}
}
