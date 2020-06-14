package org.macula.cloud.log.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.lang.Nullable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceInvokeExpressionEvaluator extends CachedExpressionEvaluator {

	private MethodBasedEvaluationContext evaluationContext;

	public ServiceInvokeExpressionEvaluator(ServiceInvokeRootObject rootObject, @Nullable BeanFactory beanFactory) {
		this.evaluationContext = new MethodBasedEvaluationContext(rootObject, rootObject.getTargetMethod(), rootObject.getArgs(),
				getParameterNameDiscoverer());
	}

	public String getExpressionValue(String expression) {
		try {
			if (StringUtils.isEmpty(expression)) {
				return expression;
			}
			Object object = this.getParser().parseExpression(expression).getValue(evaluationContext, Object.class);
			if (object != null) {
				return object.toString();
			}
		} catch (Exception ex) {
			log.error("getExpressionValue {} error: ", expression, ex);
		}
		return expression;
	}
}
