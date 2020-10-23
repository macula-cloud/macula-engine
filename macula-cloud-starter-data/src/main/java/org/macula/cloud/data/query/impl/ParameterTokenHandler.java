package org.macula.cloud.data.query.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.macula.cloud.core.principal.SubjectPrincipal;
import org.macula.cloud.core.utils.StringUtils;
import org.macula.cloud.data.query.TokenHandler;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * <p> <b>ParameterTokenHandler</b> 是参数处理. </p>
 */
public class ParameterTokenHandler implements TokenHandler {

	private final SubjectPrincipal userContext;
	private final Map<String, Object> dataContext;
	private final SpelExpressionParser parser = new SpelExpressionParser();

	public ParameterTokenHandler(SubjectPrincipal userContext, Map<String, Object> params) {
		this.userContext = userContext;
		this.dataContext = params;
	}

	@Override
	public String handleToken(String content) {
		Expression expression = parser.parseExpression(content);
		EvaluationContext evaluationContext = new StandardEvaluationContext(userContext);
		Serializable result = (Serializable) expression.getValue(evaluationContext, Object.class);
		String paramName = generateNextParamName(content, dataContext);
		if (result instanceof Collection && ((Collection<?>) result).isEmpty()) {
			result = (Serializable) Arrays.asList(StringUtils.EMPTY);
		}
		dataContext.put(paramName, result);
		return ":" + paramName;
	}

	protected String generateNextParamName(String content, Map<String, Object> dataContext) {
		int index = dataContext.size();
		return "ptoken" + index;
	}
}
