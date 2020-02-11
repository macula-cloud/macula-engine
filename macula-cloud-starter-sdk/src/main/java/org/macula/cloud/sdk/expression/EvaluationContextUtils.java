package org.macula.cloud.sdk.expression;

import org.macula.cloud.sdk.principal.SubjectPrincipal;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class EvaluationContextUtils {

	public static EvaluationContext createEvaluationContext(SubjectPrincipal subjectPrincipal) {
		EvaluationContext evalutionContext = new StandardEvaluationContext(subjectPrincipal);
		evalutionContext.getPropertyAccessors()
				.add(new CustomMethodPropertyAccessor(SubjectPrincipal.class, "getUserProperty", null));
		return evalutionContext;
	}

	public static Boolean evaluate(SubjectPrincipal principal, String expression) {
		try {
			ExpressionParser parser = new SpelExpressionParser();
			EvaluationContext context = createEvaluationContext(principal);
			Expression exp = parser.parseExpression(expression);
			return exp.getValue(context, Boolean.class);
		} catch (RuntimeException e) {
			return false;
		}
	}
}
