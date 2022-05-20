package org.macula.engine.message.channel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.macula.engine.commons.event.BroadcastMessage;
import org.macula.engine.commons.event.MaculaRemoteEvent;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.ApplicationListener;
import org.springframework.integration.support.MessageBuilder;

@Slf4j
@AllArgsConstructor
public class KafkaMessageChannelExchange implements ApplicationListener<MaculaRemoteEvent> {

	private StreamBridge streamBridge;
	private String distination;

	@Override
	public void onApplicationEvent(MaculaRemoteEvent event) {
		BroadcastMessage<?> source = event.getSource();
		if (log.isTraceEnabled()) {
			log.trace("Publish {} -> from [{}] payload [{}] ", source.getClass().getSimpleName(),
					source.getSourceApplication(), source.getPayload());
		}
		streamBridge.send(distination, MessageBuilder.withPayload(source).build());
	}
}
