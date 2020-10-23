package org.macula.cloud.core.web.bind;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String, Date> {

	private final static List<String> DATE_FORMATTER = Arrays.asList("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd");

	/**
	 * 自定义日期格式转换器
	 *
	 * @param source
	 * @return
	 */
	@Override
	public Date convert(String source) {
		for (String pattern : DATE_FORMATTER) {
			Date d = convertByPattern(source, pattern);
			if (d != null) {
				return d;
			}
		}
		return null;
	}

	protected Date convertByPattern(String source, String pattern) {
		try { // 1. 定义日期格式
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			// 2. 解析日期
			return format.parse(source);
		} catch (ParseException e) {
			// IGNORE
			return null;
		}
	}
}
