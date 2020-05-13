package org.macula.cloud.core.event;

import javax.annotation.Resource;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloudKafkaMessagePush implements ApplicationListener<CloudEvent<? extends ApplicationEvent>> {

	@Resource(name = CloudEventStreamChannel.CLOUD_BUSINESS_EVENT_OUTPUT)
	private MessageChannel businessMessageChannel;

	@Resource(name = CloudEventStreamChannel.CLOUD_BROADCAST_EVENT_OUTPUT)
	private MessageChannel broadcastMessageChannel;

	@Override
	public void onApplicationEvent(CloudEvent<? extends ApplicationEvent> event) {
		if (event.isSourceInstance()) {
			if (log.isTraceEnabled()) {
				log.trace("Publish {} -> {}({}) ", event.getClass().getSimpleName(),
						event.getSource().getSource().getClass().getSimpleName(),
						JSONObject.toJSONString(event.getSource().getSource()));
			}
			if (event instanceof BroadcastEvent) {
				broadcastMessageChannel.send(MessageBuilder.withPayload(event).build());
			}
			if (event instanceof InstanceProcessEvent) {
				businessMessageChannel.send(MessageBuilder.withPayload(event).build());
			}
		}
	}
}
