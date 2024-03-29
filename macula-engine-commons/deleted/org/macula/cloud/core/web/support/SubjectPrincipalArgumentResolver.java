//package org.macula.cloud.core.web.support;
//
//import org.macula.cloud.core.principal.SubjectPrincipal;
//import org.macula.cloud.core.utils.SecurityUtils;
//import org.springframework.core.MethodParameter;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//public class SubjectPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
//
//	@Override
//	public boolean supportsParameter(MethodParameter parameter) {
//		return SubjectPrincipal.class.isAssignableFrom(parameter.getParameterType());
//	}
//
//	@Override
//	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
//			WebDataBinderFactory binderFactory) throws Exception {
//		return SecurityUtils.getSubjectPrincipal();
//	}
//}