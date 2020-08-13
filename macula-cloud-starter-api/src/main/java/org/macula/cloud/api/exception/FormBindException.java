package org.macula.cloud.api.exception;

import java.util.ArrayList;
import java.util.List;

import org.macula.cloud.api.context.CloudApplicationContext;
import org.macula.cloud.api.protocol.ApiConstants;
import org.macula.cloud.api.protocol.FieldError;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import lombok.Getter;

@Getter
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
									objectError.getObjectName() + "." + ((org.springframework.validation.FieldError) objectError).getField(),
									CloudApplicationContext.getMessage(objectError));
							fieldErrors.add(fieldError);
						} else {
							FieldError fieldError = new FieldError(((org.springframework.validation.FieldError) objectError).getField(),
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

	@Override
	public String getParentCode() {
		return ApiConstants.MACULA_CORE_VALID_CODE;
	}

}
