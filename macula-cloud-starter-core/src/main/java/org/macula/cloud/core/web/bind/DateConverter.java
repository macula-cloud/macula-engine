package org.macula.cloud.core.web.bind;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.macula.cloud.api.exception.ApiArgumentException;
import org.springframework.core.convert.converter.Converter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateConverter implements Converter<String, Date> {

	private final static String DATE_FORMATTER = "yyyy-MM-dd";

	/**
	 * 自定义日期格式转换器
	 *
	 * @param source
	 * @return
	 */
	@Override
	public Date convert(String source) {
		try { // 1. 定义日期格式
			SimpleDateFormat format = new SimpleDateFormat(DateConverter.DATE_FORMATTER);
			// 2. 解析日期
			return format.parse(source);
		} catch (ParseException e) {
			log.error("format parse error:", e);
			throw new ApiArgumentException("请求参数时间格式错误!");
		}
	}
}
