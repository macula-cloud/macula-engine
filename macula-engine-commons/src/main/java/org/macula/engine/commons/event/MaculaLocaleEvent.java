package org.macula.engine.commons.event;

import org.macula.engine.assistant.constants.Versions;

import org.springframework.context.ApplicationEvent;

/**
 * <p>
 * <b>MaculaRemoteEvent</b> 自定义应用事件的基类
 * </p>
 */
public final class MaculaLocaleEvent extends ApplicationEvent {

	private static final long serialVersionUID = Versions.serialVersion;

	/**
	 * @param source
	 */
	private MaculaLocaleEvent(BroadcastMessage<?> source) {
		super(source);
	}

	@Override
	public BroadcastMessage<?> getSource() {
		return (BroadcastMessage<?>) super.getSource();
	}

	public static MaculaLocaleEvent of(BroadcastMessage<?> source) {
		return new MaculaLocaleEvent(source);
	}
}
