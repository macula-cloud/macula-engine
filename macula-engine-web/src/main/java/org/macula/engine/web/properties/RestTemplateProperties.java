package org.macula.engine.web.properties;

import org.macula.engine.web.constants.WebConstants;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p> Rest Template Properties Settings </p>
 */
@ConfigurationProperties(prefix = WebConstants.PROPERTY_REST_TEMPLATE)
public class RestTemplateProperties {
	/**
	 * RestTemplate 读取超时5秒,默认无限限制,单位：毫秒
	 */
	private int readTimeout = 15000;
	/**
	 * 连接超时15秒，默认无限制，单位：毫秒
	 */
	private int connectTimeout = 15000;

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
}
