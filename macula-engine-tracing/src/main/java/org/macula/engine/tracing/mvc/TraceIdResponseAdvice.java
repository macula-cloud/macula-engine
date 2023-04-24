package org.macula.engine.tracing.mvc;

import com.google.common.collect.ImmutableMap;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.macula.engine.assistant.protocol.Result;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * TraceId Response Advice
 */
@ControllerAdvice
public class TraceIdResponseAdvice implements ResponseBodyAdvice<Object> {

	private static final String TRACE_ID = "traceId";

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		String traceId = TraceContext.traceId();
		if (body == null) {
			body = ImmutableMap.of(TRACE_ID, traceId);
		} else if (body instanceof Result) {
			((Result<?>) body).setTraceId(traceId);
		}
		return body;
	}

}
