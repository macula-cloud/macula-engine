package org.macula.engine.commons.utils;

import java.io.IOException;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

import org.springframework.util.ResourceUtils;

/**
 * <p>Description: Antisamy Singleton Utility </p>
 */
@Slf4j
public class XssUtils {

	private static volatile XssUtils INSTANCE;
	private final AntiSamy antiSamy;
	private final String nbsp;
	private final String quot;

	private XssUtils() {
		Policy policy = createPolicy();
		this.antiSamy = ObjectUtils.isNotEmpty(policy) ? new AntiSamy(policy) : new AntiSamy();
		this.nbsp = cleanHtml("&nbsp;");
		this.quot = cleanHtml("\"");
	}

	private static XssUtils getInstance() {
		if (ObjectUtils.isEmpty(INSTANCE)) {
			synchronized (XssUtils.class) {
				if (ObjectUtils.isEmpty(INSTANCE)) {
					INSTANCE = new XssUtils();
				}
			}
		}

		return INSTANCE;
	}

	private Policy createPolicy() {
		try {
			URL url = ResourceUtils.getURL("classpath:antisamy/antisamy-anythinggoes.xml");
			return Policy.getInstance(url);
		} catch (IOException | PolicyException e) {
			log.warn("[Macula] |- Antisamy create policy error! {}", e.getMessage());
			return null;
		}
	}

	private CleanResults scan(String taintedHtml) throws ScanException, PolicyException {
		return antiSamy.scan(taintedHtml);
	}

	private String cleanHtml(String taintedHtml) {
		try {
			log.trace("[Macula] |- Before Antisamy Scan, value is: [{}]", taintedHtml);

			// 使用AntiSamy清洗数据
			final CleanResults cleanResults = scan(taintedHtml);
			String result = cleanResults.getCleanHTML();
			log.trace("[Macula] |- After  Antisamy Scan, value is: [{}]", result);
			return result;
		} catch (ScanException | PolicyException e) {
			log.error("[Macula] |- Antisamy scan catch error! {}", e.getMessage());
			return taintedHtml;
		}
	}

	public static String cleaning(String taintedHTML) {
		// 对转义的HTML特殊字符（<、>、"等）进行反转义，因为AntiSamy调用scan方法时会将特殊字符转义
		String cleanHtml = StringEscapeUtils.unescapeHtml4(getInstance().cleanHtml(taintedHTML));
		//AntiSamy会把“&nbsp;”转换成乱码，把双引号转换成"&quot;" 先将&nbsp;的乱码替换为空，双引号的乱码替换为双引号
		String temp = cleanHtml.replaceAll(getInstance().nbsp, "");
		String result = temp.replaceAll(getInstance().quot, "\"");
		log.trace("[Macula] |- After  Antisamy Well Formed, value is: [{}]", result);
		return result;
	}
}
