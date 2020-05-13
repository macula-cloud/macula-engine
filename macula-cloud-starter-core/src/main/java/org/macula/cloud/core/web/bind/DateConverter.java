package org.macula.cloud.core.web.bind;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.macula.cloud.core.exception.MaculaCloudException;
import org.springframework.core.convert.converter.Converter;

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
			e.printStackTrace();
			throw new MaculaCloudException("请求参数时间格式错误!");
		}
	}
}
