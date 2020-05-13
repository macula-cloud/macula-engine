package org.macula.cloud.core.event;

import org.springframework.context.ApplicationEvent;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * <b>MaculaEvent</b> 自定义应用事件的基类
 * </p>
 */
public abstract class MaculaEvent<T extends Serializable> extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	protected T source;

	/** Application Scope **/
	protected List<String> scope;

	public MaculaEvent(T source) {
		super(source);
		this.source = source;
	}

	@Override
	public T getSource() {
		return source;
	}

	public List<String> getScope() {
		return scope;
	}

	public void setScope(List<String> scope) {
		this.scope = scope;
	}
}
