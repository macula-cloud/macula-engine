package org.macula.cloud.sdk.application;


import java.io.Serializable;

import org.macula.cloud.sdk.utils.StringUtils;

/**
 * <p>
 * <b>ApplicationId</b> 是实例信息.
 * </p>
 */
public class ApplicationId implements Serializable {

	private static final long serialVersionUID = 1L;

	private static ApplicationId current;

	private final String groupId;
	private final String applicationId;
	private final String instanceId;

	public ApplicationId(String groupId, String applicationId, String instanceId) {
		this.groupId = groupId;
		this.applicationId = applicationId;
		this.instanceId = instanceId;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return this.groupId;
	}

	/**
	 * @return the applicationId
	 */
	public String getApplicationId() {
		return this.applicationId;
	}

	/**
	 * @return the instanceId
	 */
	public String getInstanceId() {
		return this.instanceId;
	}

	public String getGroupKey() {
		return this.getGroupId();
	}

	public String getApplicationKey() {
		return getGroupKey().concat("-").concat(this.applicationId);
	}

	public String getInstanceKey() {
		return getApplicationKey().concat("-").concat(this.instanceId);
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
		return StringUtils.equals(getGroupKey(), applicationId.getGroupKey());
	}

	public boolean sameApplication(ApplicationId applicationId) {
		if (applicationId == null) {
			return false;
		}
		return StringUtils.equals(getApplicationKey(), applicationId.getApplicationKey());
	}

	public boolean sameInstance(ApplicationId applicationId) {
		if (applicationId == null) {
			return false;
		}
		return StringUtils.equals(getInstanceKey(), applicationId.getInstanceKey());
	}

	public static ApplicationId current() {
		return current;
	}

	public static void setCurrent(ApplicationId current) {
		ApplicationId.current = current;
	}

}
