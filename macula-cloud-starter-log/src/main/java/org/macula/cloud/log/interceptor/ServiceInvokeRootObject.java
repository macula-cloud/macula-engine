package org.macula.cloud.log.interceptor;

import java.lang.reflect.Method;

import lombok.Getter;

@Getter
public class ServiceInvokeRootObject {

	private final Method method;

	private final Object[] args;

	private final Object target;

	private final Class<?> targetClass;

	private final Method targetMethod;

	private Object result;
	private Exception e;

	public ServiceInvokeRootObject(Method method, Object[] args, Object target, Class<?> targetClass, Method targetMethod) {
		this.method = method;
		this.target = target;
		this.targetClass = targetClass;
		this.args = args;
		this.targetMethod = targetMethod;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}

	/**
	 * @param ex the ex to set
	 */
	public void setE(Exception ex) {
		this.e = ex;
	}

}
