package org.macula.cloud.event;

import org.macula.cloud.core.event.CloudEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * 未指定Kafka监听时，使用内部直接发送，用于开发和测试环境
 */
public class MissingKafkaConfigureEventListener
		implements ApplicationListener<CloudEvent<? extends ApplicationEvent>>, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void onApplicationEvent(CloudEvent<? extends ApplicationEvent> event) {
		if (event.shouldResponse()) {
			applicationContext.publishEvent(event.getSource());
			event.done();
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
