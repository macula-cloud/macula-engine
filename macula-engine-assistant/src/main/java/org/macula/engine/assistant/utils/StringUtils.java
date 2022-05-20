package org.macula.engine.assistant.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import lombok.extern.slf4j.Slf4j;
import org.macula.engine.assistant.constants.SymbolConstants;
import org.macula.engine.assistant.protocol.Protocol;

/**
 * <p>String Utility Class</p>
 */
@Slf4j
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	/**
	 * 检测地址相关字符串是否以"/"结尾，如果没有就帮助增加一个 ""/""
	 *
	 * @param url http 请求地址字符串
	 * @return 结构合理的请求地址字符串
	 */
	public static String wellFormed(String url) {
		if (StringUtils.endsWith(url, SymbolConstants.FORWARD_SLASH)) {
			return url;
		} else {
			return url + SymbolConstants.FORWARD_SLASH;
		}
	}

	/**
	 * 获取运行主机ip地址
	 * @return ip地址，或者null
	 */
	public static String getHostAddress() {
		InetAddress address;
		try {
			address = InetAddress.getLocalHost();
			return address.getHostAddress();
		} catch (UnknownHostException e) {
			log.error("[MAULA] |- Get host address error: {}, return localhost instead", e.getLocalizedMessage());
			return "localhost";
		}
	}

	/**
	 * 将IP地址加端口号，转换为http地址。
	 *
	 * @param address             ip地址加端口号，格式：ip:port
	 * @param protocol            http协议类型 {@link Protocol}
	 * @param endWithForwardSlash 是否在结尾添加“/”
	 * @return http格式地址
	 */
	public static String addressToUri(String address, Protocol protocol, boolean endWithForwardSlash) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(protocol.getFormat());

		if (endWithForwardSlash) {
			stringBuilder.append(wellFormed(address));
		} else {
			stringBuilder.append(address);
		}

		return stringBuilder.toString();
	}

	/**
	 * 将IP地址加端口号，转换为http地址。
	 *
	 * @param address             ip地址加端口号，格式：ip:port
	 * @param endWithForwardSlash 是否在结尾添加“/”
	 * @return http格式地址
	 */
	public static String addressToUri(String address, boolean endWithForwardSlash) {
		return addressToUri(address, Protocol.HTTP, endWithForwardSlash);
	}

	/**
	 * 将IP地址加端口号，转换为http地址。
	 *
	 * @param address ip地址加端口号，格式：ip:port
	 * @return http格式地址
	 */
	public static String addressToUri(String address) {
		return addressToUri(address, false);
	}

}
