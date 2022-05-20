package org.macula.cloud.core.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidatorUtils {

	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	/**
	 * 校验对象
	 * 
	 * @param object 待校验对象
	 * @param groups 待校验的组
	 */
	public static void validateEntity(Object object, Class<?>... groups) {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
		if (!constraintViolations.isEmpty()) {
			StringBuilder msg = new StringBuilder();
			for (ConstraintViolation<Object> constraint : constraintViolations) {
				msg.append(constraint.getMessage()).append("<br>");
			}
			if (log.isErrorEnabled()) {
				log.error("validateEntity {} error : {} ", object, msg.toString());
			}
			throw new ConstraintViolationException(constraintViolations);
		}
	}
}
