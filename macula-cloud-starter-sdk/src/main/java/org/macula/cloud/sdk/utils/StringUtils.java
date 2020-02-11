package org.macula.cloud.sdk.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

	private static String ATOM = "[^\\x00-\\x1F^\\(^\\)^\\<^\\>^\\@^\\,^\\;^\\:^\\\\^\\\"^\\.^\\[^\\]^\\s]";

	private static String DOMAIN = String.format("(%s+(\\.%s+)*", ATOM, ATOM);

	private static String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";

	private static String EMAIL_REGEX = String.format("^%s+(\\.%s+)*@%s|%s)$", ATOM, ATOM, DOMAIN, IP_DOMAIN);

	private static Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

	/**
	 * 将中文作为两个字符长度截取指定长度的字符串
	 * 
	 * @param str    源字符串
	 * @param length 需要截取的长度
	 */
	public static String substringOfUnicode(String str, int length) {
		if (str == null || str.isEmpty() || length <= 0 || str.length() * 2 < length) {
			return str;
		}
		StringBuffer result = new StringBuffer();
		for (int i = 0, l = str.length(), count = 0; i < l; i++) {
			char x = str.charAt(i);
			count += x > 127 ? 2 : 1;
			if (count > length) {
				break;
			}
			result.append(x);
		}
		return result.toString();
	}

	/**
	 * 是否是合法的Email
	 * 
	 * @param value email地址
	 * @return boolean
	 */
	public static boolean isValidEmail(Object value) {
		if (value == null) {
			return false;
		}
		if (!(value instanceof String)) {
			return false;
		}
		String string = (String) value;
		if (string.length() == 0) {
			return false;
		}
		Matcher m = EMAIL_PATTERN.matcher(string);
		return m.matches();
	}

	/**
	 * 手机号验证
	 * 
	 * @param mobile  手机号码
	 * @param country 国家/地区码，目前只支持CN(iso 3166-1 alpha-2 country code)
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String mobile, String country) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		if ("CN".equals(country)) {
			p = Pattern.compile("^[1][3,4,5,6,7,8][0-9]{9}$"); // 验证手机号
			m = p.matcher(mobile);
			b = m.matches();
		}
		return b;
	}

	/**
	 * 电话号码验证
	 * 
	 * @param phone
	 * @param country 国家/地区码，目前只支持CN(iso 3166-1 alpha-2 country code)
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String phone, String country) {
		Pattern p1 = null, p2 = null;
		Matcher m = null;
		boolean b = false;
		if ("CN".equals(country)) {
			p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
			p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
			if (phone.length() > 9) {
				m = p1.matcher(phone);
				b = m.matches();
			} else {
				m = p2.matcher(phone);
				b = m.matches();
			}
		}
		return b;
	}

	public static String stripContent(String s) {
		return strip(s, "\r\n\t ");
	}

}
