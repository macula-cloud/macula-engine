//package org.macula.cloud.core.query;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import org.macula.cloud.core.expression.EvaluationContextUtils;
//import org.macula.cloud.core.principal.SubjectPrincipal;
//import org.macula.cloud.core.utils.StringUtils;
//import org.springframework.expression.EvaluationContext;
//import org.springframework.expression.Expression;
//import org.springframework.expression.TypedValue;
//import org.springframework.expression.common.ExpressionUtils;
//import org.springframework.expression.spel.standard.SpelExpressionParser;
//
//public class VariableTokenHandler implements TokenHandler {
//
//	private final SubjectPrincipal subjectPrincipal;
//	private final SpelExpressionParser parser = new SpelExpressionParser();
//
//	public VariableTokenHandler(SubjectPrincipal subjectPrincipal) {
//		this.subjectPrincipal = subjectPrincipal;
//	}
//
//	@Override
//	public String handleToken(final String content) {
//
//		String contentValue = content;
//
//		boolean useQuote = isUseQuote(contentValue);
//		if (useQuote) {
//			contentValue = contentValue.substring(1, contentValue.length() - 1);
//		}
//
//		Expression expression = parser.parseExpression(contentValue);
//		EvaluationContext evaluationContext = EvaluationContextUtils.createEvaluationContext(subjectPrincipal);
//
//		Object result = expression.getValue(evaluationContext);
//
//		if (result instanceof Collection) {
//			List<String> stringList = new ArrayList<String>();
//			for (Object item : (Collection<?>) result) {
//				String itemValue = ExpressionUtils.convertTypedValue(evaluationContext, new TypedValue(item), String.class);
//				itemValue = filter(itemValue, useQuote);
//				stringList.add(itemValue);
//			}
//			if (stringList.isEmpty()) {
//				stringList.add(filter(StringUtils.EMPTY, useQuote));
//			}
//			result = stringList;
//			return ExpressionUtils.convertTypedValue(evaluationContext, new TypedValue(result), String.class);
//		}
//
//		return filter(ExpressionUtils.convertTypedValue(evaluationContext, new TypedValue(result), String.class), useQuote);
//	}
//
//	protected boolean isUseQuote(String content) {
//		return content.startsWith("'") && content.endsWith("'");
//	}
//
//	protected String filter(String content, boolean useQuote) {
//		String contentValue = filterInject(content);
//		return useQuote ? "'" + contentValue + "'" : contentValue;
//	}
//
//	protected String filterInject(String content) {
//		if (StringUtils.isBlank(content)) {
//			return content;
//		}
//		return content.replaceAll("'", "''");
//	}
//
//}
