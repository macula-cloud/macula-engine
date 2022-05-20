package org.macula.plugin.core;

import lombok.extern.slf4j.Slf4j;
import org.macula.engine.commons.event.BroadcastMessage;
import org.macula.engine.commons.event.MaculaRemoteEvent;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BroadcastEventTestListener implements ApplicationListener<MaculaRemoteEvent> {

	@Override
	public void onApplicationEvent(MaculaRemoteEvent events) {
		BroadcastMessage<?> source = events.getSource();
		log.info("== Received BroadcastMessage: [{}] from [{}], payload: [{}]", source.getClass(),
				source.getSourceApplication(), source.getPayload());
	}
}
