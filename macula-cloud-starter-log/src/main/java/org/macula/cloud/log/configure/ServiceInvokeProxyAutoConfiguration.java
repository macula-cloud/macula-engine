package org.macula.cloud.log.configure;

import org.macula.cloud.log.interceptor.AnnotationServiceInvokeAspect;
import org.macula.cloud.log.service.ServiceInvokeLogService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class ServiceInvokeProxyAutoConfiguration {

	@Bean
	public ServiceInvokeLogService serviceInvokeLogService() {
		return new ServiceInvokeLogService();
	}

	@Bean
	AnnotationServiceInvokeAspect AnnotationServiceInvokeAspect(ServiceInvokeLogService serviceInvokeLogService) {
		return new AnnotationServiceInvokeAspect(serviceInvokeLogService);
	}
}
