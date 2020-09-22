package org.macula.cloud.core.utils;

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.macula.cloud.cache.utils.J2CacheUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UriUtils;

public final class HttpRequestUtils {

	private static final String AJAX_MARK_UP = "isAjaxRequest";

	public static final String AJAX_REQUEST_HEADER = "X-Requested-With";

	public static final String AJAX_REQUEST_VALUE = "XMLHttpRequest";

	public static final String API_REQUEST_VALUE = "OpenAPIRequest";

	public static final String FEIGN_REQUEST_VALUE = "FeignRequest";

	public static final String GATEWAY_USER_REQUEST_VALUE = "GatewayUserRequest";

	public static final String GATEWAY_OPEN_SERVICE_REQUEST_VALUE = "GatewayOpenServiceRequest";

	public static final String REDIRECT_TO_PARAMETER = "redirect_to_url";

	public static final String OAUTH2_TOKEN_COOKIE = "OAUTH2-TOKEN";

	public static boolean isAjaxOrOpenAPIRequest(HttpServletRequest request) {
		String requestType = request.getHeader(AJAX_REQUEST_HEADER);
		return AJAX_REQUEST_VALUE.equals(requestType) || API_REQUEST_VALUE.equals(requestType);
	}

	public static boolean isAjaxRequest(HttpServletRequest request) {
		String requestType = request.getHeader(AJAX_REQUEST_HEADER);
		return AJAX_REQUEST_VALUE.equals(requestType);
	}

	public static boolean isOpenAPIRequest(HttpServletRequest request) {
		String requestType = request.getHeader(AJAX_REQUEST_HEADER);
		return API_REQUEST_VALUE.equals(requestType);
	}

	public static boolean isFeignRequest(HttpServletRequest request) {
		String requestType = request.getHeader(AJAX_REQUEST_HEADER);
		return FEIGN_REQUEST_VALUE.equals(requestType);
	}

	public static boolean isGatewayUserRequest(HttpServletRequest request) {
		String requestType = request.getHeader(AJAX_REQUEST_HEADER);
		return GATEWAY_USER_REQUEST_VALUE.equals(requestType);
	}

	public static boolean isGatewayOpenServiceRequest(HttpServletRequest request) {
		String requestType = request.getHeader(AJAX_REQUEST_HEADER);
		return GATEWAY_OPEN_SERVICE_REQUEST_VALUE.equals(requestType);
	}

	public static void markAsAjaxRequest(HttpServletRequest request) {
		if (request.getAttribute(AJAX_MARK_UP) == null) {
			request.setAttribute(AJAX_MARK_UP, Boolean.TRUE);
		}
	}

	public static boolean isMarkAsAjaxRequest(HttpServletRequest request) {
		return Boolean.TRUE == request.getAttribute(AJAX_MARK_UP);
	}

	public static String getRequestBrowser(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}

	public static String getRequestAddress(HttpServletRequest request) {
		String[] headNames = new String[] { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP" };
		String ip = null;
		for (int i = 0; i < headNames.length; i++) {
			ip = request.getHeader(headNames[i]);
			if (!StringUtils.isEmpty(ip)) {
				break;
			}
		}
		return StringUtils.isEmpty(ip) ? request.getRemoteAddr() : ip;
	}

	public static Map<String, String> getOpenApiRequestParams(HttpServletRequest request) {
		TreeMap<String, String> params = new TreeMap<String, String>();
		Enumeration<?> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			if (!"SIGN".equalsIgnoreCase(name)) {
				String[] values = request.getParameterValues(name);
				if (values != null) {
					StringBuffer str = new StringBuffer(values[0]);
					// 多值合并
					for (int i = 1; i < values.length; i++) {
						str.append(name).append(values[i]);
					}
					params.put(name, str.toString());
				}
			}
		}
		return params;
	}

	public static boolean isLoginRequest(HttpServletRequest request) {
		AntPathMatcher pathMather = new AntPathMatcher();
		return pathMather.match("/login/**", request.getRequestURI());
	}

	public static boolean isLoginRedirectLocation(String location) {
		return StringUtils.contains(location, "/login");
	}

	public static void setRedirectToAttribute(HttpServletRequest request, String url) {
		request.setAttribute(REDIRECT_TO_PARAMETER, url);
	}

	public static String createRedirectToParam(String url) {
		String redirect = UriUtils.encode(url, "UTF-8");
		return new StringBuilder(REDIRECT_TO_PARAMETER).append("=").append(redirect).toString();
	}

	public static String getRedirectToValue(HttpServletRequest request) {
		String targetUrl = request.getParameter(REDIRECT_TO_PARAMETER);
		if (targetUrl == null) {
			String state = request.getParameter("state");
			if (state != null) {
				targetUrl = J2CacheUtils.get("state", state);
			}
		}
		if (targetUrl == null) {
			targetUrl = (String) request.getAttribute(REDIRECT_TO_PARAMETER);
		}
		return targetUrl;
	}

	public static void clearRedirectToValue(HttpServletRequest request) {
		String state = request.getParameter("state");
		if (state != null) {
			J2CacheUtils.evict("state", state);
		}
	}
}
