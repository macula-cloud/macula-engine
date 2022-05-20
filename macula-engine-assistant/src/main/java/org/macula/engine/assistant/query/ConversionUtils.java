package org.macula.engine.assistant.query;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionService;

/**
 * <p> <b>ConversionUtils</b> 是类型转换助手 </p>
 */
@Slf4j
public final class ConversionUtils {

	private static final ConversionUtils INSTANCE = new ConversionUtils();
	private ConversionService conversionService;

	public static ConversionUtils of(ConversionService conversionService) {
		INSTANCE.conversionService = conversionService;
		return INSTANCE;
	}

	public static <T> T convertQuietly(Object source, Class<T> targetType) {
		try {
			return INSTANCE.conversionService.convert(source, targetType);
		} catch (ConversionException e) {
			if (log.isWarnEnabled()) {
				log.warn(String.format("Conversion [%s] to [%s] failed:", source, targetType), e);
			}
			return null;
		}
	}

}
