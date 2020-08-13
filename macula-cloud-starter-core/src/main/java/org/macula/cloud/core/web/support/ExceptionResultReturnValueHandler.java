package org.macula.cloud.core.web.support;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.macula.cloud.api.protocol.Response;
import org.macula.cloud.core.utils.HttpRequestUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

/**
 * <p>
 * <b>ExceptionResultReturnValueHandler</b> 根据原始抛出异常的方法是否含有ResponseBody注解来决定异常返回的内容格式
 * </p>
 * 
 */
public class ExceptionResultReturnValueHandler extends RequestResponseBodyMethodProcessor {

	/**
	 * @param messageConverters
	 */
	public ExceptionResultReturnValueHandler(List<HttpMessageConverter<?>> messageConverters) {
		super(messageConverters);
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return Response.class.isAssignableFrom(returnType.getParameterType());
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest)
			throws IOException, HttpMediaTypeNotAcceptableException {
		if (HttpRequestUtils.isAjaxOrOpenAPIRequest(webRequest.getNativeRequest(HttpServletRequest.class))) {
			mavContainer.setRequestHandled(true);
			if (returnValue != null) {
				super.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
			}
		} else {
			mavContainer.setRequestHandled(false);
			mavContainer.addAttribute("errors", returnValue);
			mavContainer.setViewName("/error");
		}
	}
}
