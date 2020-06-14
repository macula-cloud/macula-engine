package org.macula.cloud.log.interceptor;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.transaction.Transactional;

import org.macula.cloud.core.application.ApplicationId;
import org.macula.cloud.core.context.CloudApplicationContext;
import org.macula.cloud.core.domain.ServiceInvokeLog;
import org.macula.cloud.log.annotation.ServiceInvokeProxy;
import org.macula.cloud.log.event.ServiceInvokeAlarmEvent;
import org.macula.cloud.log.repository.ServiceInvokeLogRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceInvokeLogService implements BeanFactoryAware {

	private final ExecutorService executors = Executors.newFixedThreadPool(2);
	private final ServiceInvokeLogRepository repository;
	private BeanFactory beanFactory;

	private static final ObjectMapper mapper = new ObjectMapper();

	public ServiceInvokeLogService(ServiceInvokeLogRepository repository) {
		this.repository = repository;
	}

	public void processServiceInvokeLog(ServiceInvokeProxy serviceInvokeProxy, ServiceInvokeRootObject rootObject, ServiceInvokeLog serviceInvokeLog)
			throws Exception {
		executors.execute(new SaveInvokeLogRunnable(serviceInvokeProxy, rootObject, serviceInvokeLog));
		// new SaveInvokeLogRunnable(serviceInvokeProxy, rootObject, serviceInvokeLog).call();
	}

	@Transactional
	public void saveServiceInvokeLog(org.macula.cloud.log.domain.ServiceInvokeLog entity) {
		repository.saveAndFlush(entity);
	}

	private class SaveInvokeLogRunnable implements Runnable {
		private final ServiceInvokeLog invokeLog;
		private final ServiceInvokeProxy serviceInvokeProxy;
		private final ServiceInvokeRootObject rootObject;

		private SaveInvokeLogRunnable(ServiceInvokeProxy serviceInvokeProxy, ServiceInvokeRootObject rootObject, ServiceInvokeLog invokeLog) {
			this.serviceInvokeProxy = serviceInvokeProxy;
			this.rootObject = rootObject;
			this.invokeLog = invokeLog;
		}

		@Override
		public void run() {
			try {
				synchronized (invokeLog) {
					ServiceInvokeExpressionEvaluator expressionEvaluator = new ServiceInvokeExpressionEvaluator(rootObject, beanFactory);

					invokeLog.setDataKey(expressionEvaluator.getExpressionValue(serviceInvokeProxy.key()));
					invokeLog.setSourceMethod(expressionEvaluator.getExpressionValue(serviceInvokeProxy.description()));

					invokeLog.setNode(ApplicationId.current().getInstanceKey());
					invokeLog.setSource(expressionEvaluator.getExpressionValue(serviceInvokeProxy.source()));
					invokeLog.setSourceMethod(expressionEvaluator.getExpressionValue(serviceInvokeProxy.sourceMethod()));
					if (rootObject.getArgs() != null && rootObject.getArgs().length == 1) {
						invokeLog.setSourceMessage(mapper.writeValueAsString(rootObject.getArgs()[0]));
					} else {
						invokeLog.setSourceMessage(mapper.writeValueAsString(rootObject.getArgs()));
					}
					invokeLog.setTarget(expressionEvaluator.getExpressionValue(serviceInvokeProxy.target()));
					invokeLog.setTargetMethod(expressionEvaluator.getExpressionValue(serviceInvokeProxy.targetMethod()));
					if (rootObject.getResult() != null) {
						invokeLog.setTargetMessage(mapper.writeValueAsString(rootObject.getTarget()));
						invokeLog.setStatus("success");
					}
					if (rootObject.getE() != null) {
						invokeLog.setExceptionMessage(mapper.writeValueAsString(rootObject.getE()));
						invokeLog.setStatus("error");
					}

					org.macula.cloud.log.domain.ServiceInvokeLog entity = null;
					if (invokeLog.getId() != null) {
						Optional<org.macula.cloud.log.domain.ServiceInvokeLog> optional = repository.findById(invokeLog.getId());
						if (optional.isPresent()) {
							entity = optional.get();
						}
					}
					if (entity == null) {
						entity = new org.macula.cloud.log.domain.ServiceInvokeLog();
					}
					invokeLog.clone(entity);
					saveServiceInvokeLog(entity);
					entity.cloneId(invokeLog);
				}

				if (serviceInvokeProxy.alarm() && invokeLog.getExceptionMessage() != null) {
					CloudApplicationContext.getContainer().publishEvent(new ServiceInvokeAlarmEvent(invokeLog));
				}
			} catch (Exception ex) {
				log.error("Save ServiceInvokeLog error : ", ex);
			}
		}

	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
