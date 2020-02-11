package org.macula.cloud.sdk.expression;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.TypedValue;

/**
 * <p>
 * <b>CustomMethodPropertyAccessor</b> 是用自定方法代替直接获取属性的策略.
 * </p>
 */
public class CustomMethodPropertyAccessor implements PropertyAccessor {

	private final Class<?> clazz;
	private final Method method;

	private List<String> canReadNames;

	public CustomMethodPropertyAccessor(Class<?> clazz, String methodName, Collection<String> names) {
		try {
			method = clazz.getMethod(methodName, String.class);
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to find " + methodName + " method in " + clazz, e);
		}
		this.clazz = clazz;
		if (names != null) {
			canReadNames = new ArrayList<String>(names);
		}
	}

	@Override
	public Class<?>[] getSpecificTargetClasses() {
		return new Class<?>[] { clazz };
	}

	@Override
	public boolean canRead(EvaluationContext context, Object target, String name) throws AccessException {
		return canReadNames == null || canReadNames.contains(name);
	}

	@Override
	public TypedValue read(EvaluationContext context, Object target, String name) throws AccessException {
		try {
			return new TypedValue(method.invoke(target, name));
		} catch (Exception e) {
			throw new AccessException(e.getMessage(), e);
		}
	}

	@Override
	public boolean canWrite(EvaluationContext context, Object target, String name) throws AccessException {
		return false;
	}

	@Override
	public void write(EvaluationContext context, Object target, String name, Object newValue) throws AccessException {
		throw new AccessException("The User cannot be set with an expression.");
	}

}
