package org.macula.engine.message.event;

import java.util.concurrent.Executor;

import org.macula.engine.commons.event.MaculaRemoteEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.ResolvableType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * <p>
 * <b>ApplicationMessageMulticaster</b> 事件广播组件，根据事件类型是同步事件还是异步事件自动选择同步还是异步执行
 * </p>
 */
public class ApplicationMessageMulticaster extends SimpleApplicationEventMulticaster {

	@Autowired(required = false)
	private ThreadPoolTaskExecutor taskExecutor;

	@Override
	public void multicastEvent(final ApplicationEvent event, ResolvableType eventType) {
		ResolvableType type = (eventType != null ? eventType : resolveEventType(event));
		for (final ApplicationListener<?> listener : getApplicationListeners(event, type)) {
			Executor executor = getTaskExecutor();
			// 存在Executor并且是异步事件，则异步响应事件
			if (executor != null && event instanceof MaculaRemoteEvent) {
				executor.execute(new Runnable() {
					@Override
					public void run() {
						invokeListener(listener, event);
					}
				});
			} else {
				invokeListener(listener, event);
			}
		}
	}

	private ResolvableType resolveEventType(ApplicationEvent event) {
		return ResolvableType.forInstance(event);
	}

	@Override
	protected Executor getTaskExecutor() {
		if (taskExecutor == null) {
			taskExecutor = new ThreadPoolTaskExecutor();
		}
		return this.taskExecutor;
	}
}
