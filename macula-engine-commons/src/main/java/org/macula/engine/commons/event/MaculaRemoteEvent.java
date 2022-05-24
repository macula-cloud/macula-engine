package org.macula.engine.commons.event;

import java.util.Arrays;

import org.macula.engine.assistant.constants.Versions;

import org.springframework.context.ApplicationEvent;

/**
 * <p>
 * <b>MaculaRemoteEvent</b> 自定义应用事件的基类
 * </p>
 */
public final class MaculaRemoteEvent extends ApplicationEvent {

	private static final long serialVersionUID = Versions.serialVersion;

	/**
	 * @param source
	 */
	public MaculaRemoteEvent(BroadcastMessage<?> source) {
		super(source);
	}

	@Override
	public BroadcastMessage<?> getSource() {
		return (BroadcastMessage<?>) super.getSource();
	}

	public static <T> MaculaRemoteEvent every(T payload, String... scopes) {
		BroadcastMessage<T> event = new BroadcastMessage<T>();
		event.setPayload(payload);
		if (scopes != null) {
			event.setScope(Arrays.asList(scopes));
		}
		return new MaculaRemoteEvent(event);
	}

	public static <T> MaculaRemoteEvent once(T payload, String... scopes) {
		BroadcastMessage<T> event = new BroadcastMessage<>();
		event.setPayload(payload);
		if (scopes != null) {
			event.setScope(Arrays.asList(scopes));
		}
		event.setApplicationOnce(true);
		return new MaculaRemoteEvent(event);
	}
}
