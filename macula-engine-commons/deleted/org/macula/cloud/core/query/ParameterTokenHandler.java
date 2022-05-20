//package org.macula.cloud.core.query;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Map;
//
//import org.macula.cloud.core.expression.EvaluationContextUtils;
//import org.macula.cloud.core.principal.SubjectPrincipal;
//import org.macula.cloud.core.utils.StringUtils;
//import org.springframework.expression.EvaluationContext;
//import org.springframework.expression.Expression;
//import org.springframework.expression.spel.standard.SpelExpressionParser;
//
//public class ParameterTokenHandler implements TokenHandler {
//
//	private final SubjectPrincipal subjectPrincipal;
//	private final Map<String, Object> dataContext;
//	private final SpelExpressionParser parser = new SpelExpressionParser();
//
//	public ParameterTokenHandler(SubjectPrincipal subjectPrincipal, Map<String, Object> params) {
//		this.subjectPrincipal = subjectPrincipal;
//		this.dataContext = params;
//	}
//
//	@Override
//	public String handleToken(String content) {
//		Expression expression = parser.parseExpression(content);
//		EvaluationContext evaluationContext = EvaluationContextUtils.createEvaluationContext(subjectPrincipal);
//		Object result = expression.getValue(evaluationContext, Object.class);
//		String paramName = generateNextParamName(content, dataContext);
//		if (result instanceof Collection && ((Collection<?>) result).isEmpty()) {
//			result = Collections.singletonList(StringUtils.EMPTY);
//		}
//		dataContext.put(paramName, result);
//		return ":" + paramName;
//	}
//
//	protected String generateNextParamName(String content, Map<String, Object> dataContext) {
//		int index = dataContext.size();
//		return "ptoken" + index;
//	}
//}
