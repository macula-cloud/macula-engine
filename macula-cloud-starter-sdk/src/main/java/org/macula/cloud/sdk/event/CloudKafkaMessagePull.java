package org.macula.cloud.sdk.event;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ApplicationEventMulticaster;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloudKafkaMessagePull {

	private ApplicationEventMulticaster applicationEventMulticaster;

	public CloudKafkaMessagePull(ApplicationEventMulticaster applicationEventMulticaster) {
		this.applicationEventMulticaster = applicationEventMulticaster;
	}

	public void onApplicationEvent(CloudEvent<? extends ApplicationEvent> event) {
		if (log.isTraceEnabled()) {
			log.trace("Receive {} -> {} : {}", event.getClass().getSimpleName(),
					event.getSource().getClass().getSimpleName(), event.getSource().getSource());
		}
		if (event.shouldResponse()) {
			applicationEventMulticaster.multicastEvent(event.getSource());
			event.done();
		}
	}

	@StreamListener(CloudEventStreamChannel.CLOUD_BROADCAST_EVENT_INPUT)
	public void handleBroadcastEvent(BroadcastEvent<? extends ApplicationEvent> event) {
		onApplicationEvent(event);
	}

	@StreamListener(CloudEventStreamChannel.CLOUD_BUSINESS_EVENT_INPUT)
	public void handleBusinessEvent(InstanceProcessEvent<? extends ApplicationEvent> event) {
		onApplicationEvent(event);
	}

}
