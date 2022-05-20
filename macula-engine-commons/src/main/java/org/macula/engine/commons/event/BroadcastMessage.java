package org.macula.engine.commons.event;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import lombok.Getter;
import lombok.Setter;
import org.macula.engine.assistant.support.ApplicationId;
import org.macula.engine.j2cache.utils.J2CacheUtils;

import org.springframework.util.CollectionUtils;

/**
 * Broadcast Message,  Communication in Group
 */
public final class BroadcastMessage<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private ApplicationId sourceApplication = ApplicationId.current();

	@Setter
	private List<String> scope = null;

	@Getter
	@Setter
	private T payload = null;

	/** 是否已经处理 */
	private boolean responsed = false;

	@Setter
	/** Process once per application */
	private boolean applicationOnce;

	@Setter
	private Long token = IdUtil.getSnowflakeNextId();

	public boolean shouldResponseByGroup() {
		ApplicationId current = ApplicationId.current();
		return !hasResponsed()
				&& (getScope() == null || getScope().contains(current.getName()))
				&& (current.sameGroup(getSourceApplication()));
	}

	public boolean shouldResponseByApplication() {
		ApplicationId current = ApplicationId.current();
		return !hasResponsed()
				&& (CollectionUtils.isEmpty(getScope()) || getScope().contains(current.getName()))
				&& (current.sameApplication(getSourceApplication()));
	}

	public boolean shouldResponseOtherInstance() {
		ApplicationId current = ApplicationId.current();
		return !hasResponsed()
				&& (CollectionUtils.isEmpty(getScope()) || getScope().contains(current.getName()))
				&& (current.sameGroup(getSourceApplication()))
				&& (!current.sameInstance(getSourceApplication()));
	}

	public boolean hasResponsed() {
		if (!responsed && applicationOnce) {
			String responseKey = ApplicationId.current().getApplicationKey() + "::" + token;
			Object proccessed = J2CacheUtils.get(J2CacheUtils.EVENTS_REGION, responseKey);
			responsed = proccessed != null;
		}
		return responsed;
	}

	public void setResponsed() {
		if (!this.responsed) {
			this.responsed = true;
			if (applicationOnce) {
				String responseKey = ApplicationId.current().getApplicationKey() + "::" + token;
				J2CacheUtils.set(J2CacheUtils.EVENTS_REGION, responseKey, this.responsed);
			}
		}
	}

	public List<String> getScope() {
		return scope != null ? Collections.unmodifiableList(this.scope) : null;
	}

}
