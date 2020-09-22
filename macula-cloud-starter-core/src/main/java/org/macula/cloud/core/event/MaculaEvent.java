package org.macula.cloud.core.event;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.ApplicationEvent;

/**
 * <p>
 * <b>MaculaEvent</b> 自定义应用事件的基类
 * </p>
 */
public abstract class MaculaEvent<T extends Serializable> extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	/** Application Scope **/
	protected List<String> scope;

	public MaculaEvent(T source) {
		super(source);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getSource() {
		return (T) source;
	}

	public List<String> getScope() {
		return scope;
	}

	public void setScope(List<String> scope) {
		this.scope = scope;
	}
}
