package org.macula.cloud.core.utils;

import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

public final class ServerRequestUtils {

	private static final String AJAX_MARK_UP = "isAjaxRequest";

	public static final String AJAX_REQUEST_HEADER = "X-Requested-With";

	public static final String AJAX_REQUEST_VALUE = "XMLHttpRequest";

	public static final String API_REQUEST_VALUE = "OpenAPIRequest";

	public static final String FEIGN_REQUEST_VALUE = "FeignRequest";

	public static final String GATEWAY_USER_REQUEST_VALUE = "GatewayUserRequest";

	public static final String GATEWAY_OPEN_SERVICE_REQUEST_VALUE = "GatewayOpenServiceRequest";

	public static boolean isAjaxOrOpenAPIRequest(ServerHttpRequest request) {
		String requestType = request.getHeaders().getFirst(AJAX_REQUEST_HEADER);
		return AJAX_REQUEST_VALUE.equals(requestType) || API_REQUEST_VALUE.equals(requestType);
	}

	public static boolean isAjaxRequest(ServerHttpRequest request) {
		String requestType = request.getHeaders().getFirst(AJAX_REQUEST_HEADER);
		return AJAX_REQUEST_VALUE.equals(requestType);
	}

	public static boolean isOpenAPIRequest(ServerHttpRequest request) {
		String requestType = request.getHeaders().getFirst(AJAX_REQUEST_HEADER);
		return API_REQUEST_VALUE.equals(requestType);
	}

	public static boolean isFeignRequest(ServerHttpRequest request) {
		String requestType = request.getHeaders().getFirst(AJAX_REQUEST_HEADER);
		return FEIGN_REQUEST_VALUE.equals(requestType);
	}

	public static boolean isGatewayUserRequest(ServerHttpRequest request) {
		String requestType = request.getHeaders().getFirst(AJAX_REQUEST_HEADER);
		return GATEWAY_USER_REQUEST_VALUE.equals(requestType);
	}

	public static boolean isGatewayOpenServiceRequest(ServerHttpRequest request) {
		String requestType = request.getHeaders().getFirst(AJAX_REQUEST_HEADER);
		return GATEWAY_OPEN_SERVICE_REQUEST_VALUE.equals(requestType);
	}

	public static void markAsAjaxRequest(ServerWebExchange request) {
		if (request.getAttribute(AJAX_MARK_UP) == null) {
			request.getAttributes().put(AJAX_MARK_UP, Boolean.TRUE);
		}
	}

	public static boolean isMarkAsAjaxRequest(ServerWebExchange request) {
		return Boolean.TRUE == request.getAttribute(AJAX_MARK_UP);
	}

	public static String getRequestBrowser(ServerHttpRequest request) {
		return request.getHeaders().getFirst("User-Agent");
	}

	public static String getRequestAddress(ServerHttpRequest request) {
		String[] headNames = new String[] { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP" };
		String ip = null;
		for (int i = 0; i < headNames.length; i++) {
			ip = request.getHeaders().getFirst(headNames[i]);
			if (!StringUtils.isEmpty(ip)) {
				break;
			}
		}
		return StringUtils.isEmpty(ip) ? request.getRemoteAddress().toString() : ip;
	}

	public static TreeMap<String, String> getOpenApiRequestParams(ServerHttpRequest request) {
		MultiValueMap<String, String> queryParams = request.getQueryParams();
		TreeMap<String, String> params = new TreeMap<String, String>();
		for (Entry<String, List<String>> entry : queryParams.entrySet()) {
			String name = entry.getKey();
			String[] values = entry.getValue().toArray(new String[0]);
			if (values != null) {
				StringBuffer str = new StringBuffer(values[0]);
				// 多值合并
				for (int i = 1; i < values.length; i++) {
					str.append(name).append(values[i]);
				}
				params.put(name, str.toString());
			}
		}
		return params;
	}

	public static boolean isLoginRequest(ServerHttpRequest request) {
		AntPathMatcher pathMather = new AntPathMatcher();
		return pathMather.match("/login/**", request.getURI().toString());
	}

	public static boolean isLoginRedirectLocation(String location) {
		return StringUtils.contains(location, "/login");
	}
}
