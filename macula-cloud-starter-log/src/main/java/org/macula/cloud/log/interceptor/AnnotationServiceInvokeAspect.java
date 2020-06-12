package org.macula.cloud.log.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.macula.cloud.log.annotation.ServiceInvokeProxy;
import org.macula.cloud.log.service.ServiceInvokeLogService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@AllArgsConstructor
public class AnnotationServiceInvokeAspect {

	private ServiceInvokeLogService serviceInvokeLogService;

	@Around("@annotation(serviceInvokeProxy)")
	public Object around(ProceedingJoinPoint joinPoint, ServiceInvokeProxy serviceInvokeProxy) throws Throwable {
		try {
			long startTime = System.currentTimeMillis();
			Object result = joinPoint.proceed();
			serviceInvokeLogService.doSomething();
			long timeTaken = System.currentTimeMillis() - startTime;
			log.info("Time Taken by {} is {}", joinPoint, timeTaken);
			return result;
		} finally {

		}
	}
}
