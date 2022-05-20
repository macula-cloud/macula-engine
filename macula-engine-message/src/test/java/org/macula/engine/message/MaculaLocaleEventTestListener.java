package org.macula.engine.message;

import lombok.extern.slf4j.Slf4j;
import org.macula.engine.commons.event.BroadcastMessage;
import org.macula.engine.commons.event.MaculaLocaleEvent;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MaculaLocaleEventTestListener implements ApplicationListener<MaculaLocaleEvent> {

	@Override
	public void onApplicationEvent(MaculaLocaleEvent message) {
		BroadcastMessage<?> event = message.getSource();
		log.info("[Macula] |- Message |- Receive [{}] -> from [{}] payload [{}] type [{}], shouldResponse? {}",
				event.getClass(), event.getSourceApplication(), event.getPayload(), event.getPayload().getClass(),
				event.shouldResponseByApplication());
	}

}
