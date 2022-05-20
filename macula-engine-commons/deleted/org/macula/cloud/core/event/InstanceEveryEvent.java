package org.macula.cloud.core.event;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.macula.engine.assistant.support.ApplicationId;

/**
 * <p>
 * <b>BroadcastEvent</b> 是分布式环境下的Application事件，需要通知所有的实例。
 * </p>
 */
public final class BroadcastEvent<T extends MaculaEvent<?>> extends CloudEvent<T> {

	private static final long serialVersionUID = 1L;

	/** 是否已经处理 */
	private boolean done;

	public static <T extends MaculaEvent<?>> BroadcastEvent<T> wrap(T source) {
		return new BroadcastEvent<T>(source);
	}

	/**
	 * @param source
	 */
	private BroadcastEvent(T source) {
		super(source);
		this.done = false;
	}

	@Override
	public boolean shouldResponse() {
		return shouldResponse(ApplicationId.current()) && !done;
	}

	private boolean shouldResponse(ApplicationId applicationId) {
		return !(done || !getApplicationId().sameGroup(ApplicationId.current()));
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public void done() {
		this.done = true;
	}

}
