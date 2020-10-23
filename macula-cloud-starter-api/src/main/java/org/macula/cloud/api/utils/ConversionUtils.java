package org.macula.cloud.api.utils;

import org.macula.cloud.api.context.CloudApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionService;

/**
 * <p> <b>ConversionUtils</b> 是类型转换助手 </p>
 */
public final class ConversionUtils {

	private ConversionUtils() {
		//Noops;
	}

	private static final Logger log = LoggerFactory.getLogger(ConversionUtils.class);

	private static final class ConversionServiceHolder {

		private static ConversionService conversionService;

		private ConversionServiceHolder() {
			//Noops
		}

		public static synchronized ConversionService getConversionService() {
			if (conversionService == null) {
				conversionService = CloudApplicationContext.getBean(ConversionService.class);
			}
			return conversionService;
		}
	}

	public static <T> T convertQuietly(Object source, Class<T> targetType) {
		try {
			return ConversionServiceHolder.getConversionService().convert(source, targetType);
		} catch (ConversionException e) {
			if (log.isWarnEnabled()) {
				log.warn(String.format("Conversion [%s] to [%s] failed:", source, targetType), e);
			}
			return null;
		}
	}

}
