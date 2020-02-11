package org.macula.cloud.sdk.utils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class OkHttpUtil {

	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static final int CONN_TIMEOUT = 10000;
	private static final int READ_TIMEOUT = 3000000;
	private static final int HTTP_STATUS_INTERNEL_SERVER_ERROR = 500;

	private static final OkHttpClient client = (new OkHttpClient()).newBuilder()
			.connectTimeout(OkHttpUtil.CONN_TIMEOUT, TimeUnit.MILLISECONDS)
			.readTimeout(OkHttpUtil.READ_TIMEOUT, TimeUnit.MILLISECONDS).build();

	public static String get(String url) throws IOException {
		Request request = (new Builder()).url(url).build();
		Response response = client.newCall(request).execute();
		if (OkHttpUtil.HTTP_STATUS_INTERNEL_SERVER_ERROR == response.code()) {
			throw new RuntimeException(
					String.format("Call url [%s], response : [%s] code [500]", url, response.body().string()));
		}
		return response.body().string();
	}

	public static String post(String url, String content) throws IOException {
		RequestBody body = RequestBody.create(JSON, content);
		Request request = (new Builder()).url(url).post(body).build();
		Response response = client.newCall(request).execute();
		if (OkHttpUtil.HTTP_STATUS_INTERNEL_SERVER_ERROR == response.code()) {
			throw new RuntimeException("服务器响应错误:" + response.body().string());
		}
		return response.body().string();
	}

	public static String post(String url, String content, Map<String, String> headers) throws IOException {
		RequestBody body = RequestBody.create(JSON, content);
		Builder post = (new Builder()).url(url).post(body);
		headers.forEach((k, v) -> post.addHeader(k, v));
		Request request = post.build();
		Response response = client.newCall(request).execute();
		if (OkHttpUtil.HTTP_STATUS_INTERNEL_SERVER_ERROR == response.code()) {
			throw new RuntimeException("服务器响应错误:" + response.body().string());
		}
		return response.body().string();
	}

}