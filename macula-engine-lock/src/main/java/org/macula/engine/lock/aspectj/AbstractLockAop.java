package org.macula.engine.lock.aspectj;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 */
@Slf4j
public class AbstractLockAop {

	/**
	 * 获取被拦截方法对象
	 * <p>
	 * MethodSignature.getMethod() 获取的是顶层接口或者父类的方法对象 而缓存的注解在实现类的方法上
	 * 所以应该使用反射获取当前对象的方法对象
	 *
	 * @param pjp
	 * @return
	 */
	Method getMethod(ProceedingJoinPoint pjp) {
		Signature sig = pjp.getSignature();
		MethodSignature msig = null;
		if (!(sig instanceof MethodSignature)) {
			throw new IllegalArgumentException("该注解只能用于方法");
		}
		msig = (MethodSignature) sig;
		Object target = pjp.getTarget();
		Method method = null;
		try {
			method = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
		} catch (NoSuchMethodException e) {
			log.error("GetMethod error:", e);
		}
		return method;
	}

	/**
	 * 获取缓存的key key 定义在注解上，支持SPEL表达式
	 *
	 * @param key
	 * @param method
	 * @param args
	 * @return
	 */
	String parseKey(String key, Method method, Object[] args) {
		// 获取被拦截方法参数名列表(使用Spring支持类库)
		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		String[] paraNameArr = u.getParameterNames(method);

		// 使用SPEL进行key的解析
		ExpressionParser parser = new SpelExpressionParser();

		// SPEL上下文
		StandardEvaluationContext context = new StandardEvaluationContext();

		// 把方法参数放入SPEL上下文中
		for (int i = 0; i < paraNameArr.length; i++) {
			context.setVariable(paraNameArr[i], args[i]);
		}
		return parser.parseExpression(key).getValue(context, String.class);
	}

	/**
	 * 支持 #p0 参数索引的表达式解析
	 *
	 * @param rootObject 根对象,method 所在的对象
	 * @param spel       表达式
	 * @param method     ，目标方法
	 * @param args       方法入参
	 * @return 解析后的字符串
	 */
	String parseKey(Object rootObject, String spel, Method method, Object[] args) {
		// 获取被拦截方法参数名列表(使用Spring支持类库)
		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		String[] paraNameArr = u.getParameterNames(method);
		// 使用SPEL进行key的解析
		ExpressionParser parser = new SpelExpressionParser();
		// SPEL上下文
		StandardEvaluationContext context = new MethodBasedEvaluationContext(rootObject, method, args, u);
		// 把方法参数放入SPEL上下文中
		for (int i = 0; i < paraNameArr.length; i++) {
			context.setVariable(paraNameArr[i], args[i]);
		}
		return parser.parseExpression(spel).getValue(context, String.class);
	}
}
