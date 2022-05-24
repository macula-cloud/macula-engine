package org.macula.engine.assistant.support;

import java.io.Serializable;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.macula.engine.assistant.constants.SymbolConstants;
import org.macula.engine.assistant.constants.Versions;

/**
 * <p>
 * <b>ApplicationId</b> 是实例信息.
 * </p>
 */
@NoArgsConstructor
public class ApplicationId implements Serializable {

	private static final long serialVersionUID = Versions.serialVersion;

	private static ApplicationId current;

	/** 应用分组，用于消息传播事相同组可以响应 */
	private String group;
	/** 应用name，对于SpringBoot应用，与spring.name相同 */
	private String name;
	/**  应用实例，对于多实例运行时的标识 */
	private String instance;

	public ApplicationId(String groupId, String applicationId, String instanceId) {
		this.group = groupId;
		this.name = applicationId;
		this.instance = instanceId;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return this.group;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the instance
	 */
	public String getInstance() {
		return this.instance;
	}

	public String getGroupKey() {
		return this.getGroup();
	}

	public String getApplicationKey() {
		return getGroupKey().concat(SymbolConstants.AT).concat(this.name);
	}

	public String getInstanceKey() {
		return getApplicationKey().concat(SymbolConstants.AT).concat(this.instance);
	}

	@Override
	public String toString() {
		return getInstanceKey();
	}

	@Override
	public int hashCode() {
		return getInstanceKey().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		ApplicationId other = (ApplicationId) obj;
		return sameGroup(other) && sameApplication(other) && sameInstance(other);
	}

	public boolean sameGroup(ApplicationId applicationId) {
		if (applicationId == null) {
			return false;
		}
		return StringUtils.equalsIgnoreCase(getGroupKey(), applicationId.getGroupKey());
	}

	public boolean sameApplication(ApplicationId applicationId) {
		if (applicationId == null) {
			return false;
		}
		return StringUtils.equalsIgnoreCase(getApplicationKey(), applicationId.getApplicationKey());
	}

	public boolean sameInstance(ApplicationId applicationId) {
		if (applicationId == null) {
			return false;
		}
		return StringUtils.equalsIgnoreCase(getInstanceKey(), applicationId.getInstanceKey());
	}

	public static ApplicationId current() {
		return current;
	}

	public static void setCurrent(ApplicationId current) {
		ApplicationId.current = current;
	}

}
