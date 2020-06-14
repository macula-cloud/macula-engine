package org.macula.cloud.log.configure;

import org.macula.cloud.log.interceptor.AnnotationServiceInvokeAspect;
import org.macula.cloud.log.interceptor.ServiceInvokeLogService;
import org.macula.cloud.log.repository.ServiceInvokeLogRepository;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@AutoConfigureOrder(AutoConfigureOrder.DEFAULT_ORDER + 100)
@EnableAspectJAutoProxy
public class ServiceInvokeProxyAutoConfiguration {

	@Bean
	public ServiceInvokeLogService serviceInvokeLogService(final ServiceInvokeLogRepository repository) {
		return new ServiceInvokeLogService(repository);
	}

	@Bean
	public AnnotationServiceInvokeAspect AnnotationServiceInvokeAspect(ServiceInvokeLogService serviceInvokeLogService) {
		return new AnnotationServiceInvokeAspect(serviceInvokeLogService);
	}
}
