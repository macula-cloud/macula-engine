package org.macula.cloud.sdk.event;

import org.macula.cloud.sdk.application.ApplicationId;
import org.macula.cloud.sdk.utils.J2CacheUtils;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

/**
 * 按Application级别，每个应用只有一个实例会响应该事件
 */
public final class InstanceProcessEvent<T extends ApplicationEvent> extends CloudEvent<T> {

	private static final long serialVersionUID = 1L;
	private static final String CacheRegion = J2CacheUtils.EVENTS_REGION;

	private String token;
	private String responseKey;
	private boolean done;

	public static <T extends MaculaEvent<?>> InstanceProcessEvent<T> wrap(T source) {
		String token = UUID.randomUUID().toString();
		return new InstanceProcessEvent<T>(source, token);
	}

	/**
	 * @param source
	 */
	private InstanceProcessEvent(T source, String token) {
		super(source);
		this.token = ApplicationId.current().getApplicationKey() + "::" + token;
		this.responseKey = token.concat("::responsed");
		this.done = false;
	}

	@Override
	public boolean shouldResponse() {
		if (done || !getApplicationId().sameGroup(ApplicationId.current())) {
			return false;
		}
		Object proccessed = J2CacheUtils.get(CacheRegion, responseKey);
		if (proccessed == null) {
			return true;
		}
		return false;
	}

	@Override
	public void done() {
		if (!this.done) {
			J2CacheUtils.set(CacheRegion, responseKey, Boolean.TRUE);
			this.done = true;
		}
	}

	public String getToken() {
		return token;
	}

}
