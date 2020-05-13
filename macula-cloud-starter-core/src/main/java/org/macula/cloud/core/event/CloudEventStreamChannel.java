package org.macula.cloud.core.event;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface CloudEventStreamChannel {

	public static String CLOUD_BROADCAST_EVENT_INPUT = "macula-broadcast-event-input";
	public static String CLOUD_BROADCAST_EVENT_OUTPUT = "macula-broadcast-event-output";

	public static String CLOUD_BUSINESS_EVENT_INPUT = "macula-business-event-input";
	public static String CLOUD_BUSINESS_EVENT_OUTPUT = "macula-business-event-output";

	@Input(CLOUD_BROADCAST_EVENT_INPUT)
	SubscribableChannel broadcastInput();

	@Output(CLOUD_BROADCAST_EVENT_OUTPUT)
	MessageChannel broadcastOutput();

	@Input(CLOUD_BUSINESS_EVENT_INPUT)
	SubscribableChannel businessInput();

	@Output(CLOUD_BUSINESS_EVENT_OUTPUT)
	MessageChannel businessOutput();

}
