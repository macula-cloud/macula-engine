package org.macula.cloud.core.utils;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import jakarta.crypto.Mac;
import jakarta.crypto.SecretKey;
import jakarta.crypto.spec.SecretKeySpec;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.SerializingConverter;

public class SignatureUtils {

	private static Converter<Object, byte[]> serializer = new SerializingConverter();

	/**
	 * 将内容MD5
	 * 
	 * @param context
	 * @return MD5 String
	 */
	public static String md5(Object context) {
		return byte2hex(md5tobytes(context));
	}

	public static byte[] md5tobytes(Object context) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (context instanceof String) {
				return md.digest(context.toString().getBytes("UTF-8"));
			}
			return md.digest(serializer.convert(context));
		} catch (Exception e) {
			throw new RuntimeException("md5 error!", e);
		}
	}

	/**
	 * 将内容SHA1
	 *
	 * @param context
	 * @return SHA1 String
	 */
	public static String sha1(Object context) {
		return byte2hex(sha1tobytes(context));
	}

	public static byte[] sha1tobytes(Object context) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			if (context instanceof String) {
				return md.digest(context.toString().getBytes("UTF-8"));
			}
			return md.digest(serializer.convert(context));
		} catch (Exception e) {
			throw new RuntimeException("sha1 error！", e);
		}
	}

	/**
	 * 新的md5签名，首尾放secret。
	 *
	 * @param params 传给服务器的参数
	 * @param secret 分配给您的APP_SECRET
	 */
	public static String md5Signature(Map<String, String> params, String secret) {
		String result = null;
		StringBuffer orgin = getBeforeSign(params, new StringBuffer(secret));
		if (orgin == null) {
			return result;
		}
		// secret last
		orgin.append(secret);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			result = byte2hex(md.digest(orgin.toString().getBytes("utf-8")));
		} catch (Exception e) {
			throw new RuntimeException("md5 sign error !", e);
		}
		return result;
	}

	/**
	 * 新的md5签名，hmac加密
	 *
	 * mysign = SignatureUtils.md5Signature(params
	 * 
	 * @param params 传给服务器的参数
	 * @param secret 分配给您的APP_SECRET
	 */
	public static String hmacSignature(Map<String, String> params, String secret) {
		String result = null;
		StringBuffer orgin = getBeforeSign(params, new StringBuffer());
		if (orgin == null) {
			return result;
		}
		try {
			result = byte2hex(encryptHMAC(orgin.toString().getBytes("utf-8"), secret));
		} catch (Exception e) {
			throw new RuntimeException("sign error !", e);
		}
		return result;
	}

	/**
	 * 二行制转字符串
	 * 
	 * @param b 二进制数组
	 */
	public static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs.append("0").append(stmp);
			} else {
				hs.append(stmp);
			}
		}
		return StringUtils.upperCase(hs.toString());
	}

	/**
	 * 添加参数的封装方法
	 * 
	 * @param params 参数
	 * @param orgin  原字符串
	 */
	private static StringBuffer getBeforeSign(Map<String, String> params, StringBuffer orgin) {
		if (params == null) {
			return null;
		}

		Map<String, String> treeMap = new TreeMap<String, String>();
		treeMap.putAll(params);
		Iterator<String> iter = treeMap.keySet().iterator();
		while (iter.hasNext()) {
			String name = iter.next();
			orgin.append(name).append(params.get(name));
		}
		return orgin;
	}

	/**
	 * HMAC加密算法
	 * 
	 * @param data 待签名字节数组
	 * @param key  密钥
	 */
	private static byte[] encryptHMAC(byte[] data, String key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key.getBytes("utf-8"), "HmacMD5");
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);
	}

}
