package org.macula.cloud.security.feign;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.macula.cloud.core.utils.SignatureUtils;
import org.macula.cloud.core.utils.SystemUtils;

public class FeignOpenApiHelper {

	public final static String APP_KEY = "app_key";

	public final static String SIGN = "sign";

	public final static String TIMESTAMP = "timestamp";

	public final static String SESSION = "session";

	public final static String SESSION_USER = "session_user";

	public final static String FORMAT = "format";

	public final static String FORMAT_XML = "xml";

	public final static String FORMAT_JSON = "json";

	public final static String VERSION = "v";

	public final static String SIGN_METHOD = "sign_method";

	public final static String SIGN_METHOD_MD5 = "md5";

	public final static String SIGN_METHOD_HMAC = "hmac";

	private final static String CHAESET = "utf-8";

	public static Map<String, String> createOpenApiSignMap(String clientId, String clientSecret, Map<String, Collection<String>> queries) {
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put(APP_KEY, clientId);
		params.put(TIMESTAMP, Long.toString((SystemUtils.getCurrentTime()).getTime()));
		TreeMap<String, String> candicateParams = new TreeMap<String, String>();
		candicateParams.putAll(params);
		if (queries != null) {
			queries.forEach((name, value) -> {
				List<String> values = new ArrayList<String>(value);
				Collections.sort(values);
				StringBuilder combine = new StringBuilder(getDecodeValue(values.get(0)));
				for (int i = 1; i < values.size(); i++) {
					combine.append(name).append(getDecodeValue(values.get(i)));
				}
				candicateParams.put(name, combine.toString());
			});
		}
		String sign = SignatureUtils.md5Signature(candicateParams, clientSecret);
		params.put(SIGN, sign);
		return params;
	}

	protected static String getDecodeValue(String value) {
		try {
			return URLDecoder.decode(value, CHAESET);
		} catch (UnsupportedEncodingException ex) {
			return value;
		}
	}
}
