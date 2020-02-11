package org.macula.cloud.sdk.event;

import org.macula.cloud.sdk.application.ApplicationId;
import org.springframework.context.ApplicationEvent;

/**
 * <p>
 * <b>AsyncMaculaEvent</b> 异步响应事件，和发送事件方不在同一个线程中
 * </p>
 */
abstract class CloudEvent<T extends ApplicationEvent> extends MaculaEvent<T> {

	private static final long serialVersionUID = 1L;

	private ApplicationId applicationId;

	/**
	 * @param source
	 */
	public CloudEvent(T source) {
		super(source);
		this.applicationId = ApplicationId.current();
	}

	public abstract boolean shouldResponse();

	public abstract void done();

	/**
	 * @return the applicationId
	 */
	public ApplicationId getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId
	 *            the applicationId to set
	 */
	public void setApplicationId(ApplicationId applicationId) {
		this.applicationId = applicationId;
	}

	public boolean isSourceInstance() {
		return isSourceInstance(ApplicationId.current());
	}

	public boolean isSourceInstance(ApplicationId applicationId) {
		return this.getApplicationId() != null && this.getApplicationId().equals(applicationId);
	}

	public boolean isSourceApplication() {
		return isSourceApplication(ApplicationId.current());
	}

	public boolean isSourceApplication(ApplicationId applicationId) {
		return this.getApplicationId() != null && this.getApplicationId().sameGroup(applicationId)
				&& this.getApplicationId().sameApplication(applicationId);
	}
}
