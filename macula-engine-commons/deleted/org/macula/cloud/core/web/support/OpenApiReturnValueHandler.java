package org.macula.cloud.core.web.support;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.macula.cloud.api.protocol.ExecuteResponse;
import org.macula.cloud.api.protocol.PageResponse;
import org.macula.cloud.api.protocol.Response;
import org.macula.cloud.core.web.annotation.OpenApi;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

/**
 * <p>
 * <b>OpenApiReturnValueHandler</b> 处理含有{@link OpenApi}注解的方法的返回值 如果返回值不是
 * {@link Response}类型、{@link Map}类型，则会转换为 {@link Response}类型
 * 返回，并且根据客户端的需要返回JSON格式或者XML格式
 * </p>
 */
public class OpenApiReturnValueHandler extends RequestResponseBodyMethodProcessor {

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return returnType.getMethodAnnotation(OpenApi.class) != null;
	}

	/**
	 * @param messageConverters
	 */
	public OpenApiReturnValueHandler(List<HttpMessageConverter<?>> messageConverters) {
		super(messageConverters);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest)
			throws IOException, HttpMediaTypeNotAcceptableException {
		mavContainer.setRequestHandled(true);
		Object newValue = returnValue;
		Class<?> returnParaType = returnType.getParameterType();
		if (!void.class.isAssignableFrom(returnParaType)) {
			// 不是Response、Map、Model等类型的返回值，需要包裹为Response类型
			if (!Response.class.isAssignableFrom(returnParaType) && !Map.class.isAssignableFrom(returnParaType)
					&& !Model.class.isAssignableFrom(returnParaType)) {

				if (Page.class.isAssignableFrom(returnType.getParameterType())) {
					newValue = new PageResponse<Object>((Page<Object>) returnValue);
				} else {
					newValue = new ExecuteResponse<Object>(returnValue);
				}
			}

			if (newValue == null) {
				newValue = new ExecuteResponse<Object>(returnValue);
			}

			writeWithMessageConverters(newValue, returnType, webRequest);
		}
	}
}
