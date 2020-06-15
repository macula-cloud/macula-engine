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

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceInvokeLogService implements BeanFactoryAware {

	private final ExecutorService executors = Executors.newFixedThreadPool(2);
	private final ServiceInvokeLogRepository repository;
	private BeanFactory beanFactory;

	public ServiceInvokeLogService(ServiceInvokeLogRepository repository) {
		this.repository = repository;
	}

	public void processServiceInvokeLog(ServiceInvokeProxy serviceInvokeProxy, ServiceInvokeRootObject rootObject, ServiceInvokeLog serviceInvokeLog)
			throws Exception {
		executors.execute(new SaveInvokeLogRunnable(serviceInvokeProxy, rootObject, serviceInvokeLog));
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

					invokeLog.setDataKey(expressionEvaluator.getStringExpression(serviceInvokeProxy.key()));
					invokeLog.setSourceMethod(expressionEvaluator.getStringExpression(serviceInvokeProxy.description()));

					invokeLog.setNode(ApplicationId.current().getInstanceKey());
					invokeLog.setSource(expressionEvaluator.getStringExpression(serviceInvokeProxy.source()));
					invokeLog.setSourceMethod(expressionEvaluator.getStringExpression(serviceInvokeProxy.sourceMethod()));
					invokeLog.setSourceMessage(expressionEvaluator.getStringExpression(serviceInvokeProxy.sourceMessage()));

					invokeLog.setTarget(expressionEvaluator.getStringExpression(serviceInvokeProxy.target()));
					invokeLog.setTargetMethod(expressionEvaluator.getStringExpression(serviceInvokeProxy.targetMethod()));
					invokeLog.setTargetMessage(expressionEvaluator.getStringExpression(serviceInvokeProxy.targetMessage()));
					invokeLog.setExceptionMessage(expressionEvaluator.getStringExpression(serviceInvokeProxy.exceptionMessage()));

					invokeLog.setStatus(expressionEvaluator.getBooleanExpression(serviceInvokeProxy.success()) ? "SUCCESS" : "ERROR");

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
			} catch (

			Exception ex) {
				log.error("Save ServiceInvokeLog error : ", ex);
			}
		}

	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
