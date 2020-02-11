package org.macula.cloud.sdk.configure.model;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import org.macula.cloud.sdk.application.ApplicationId;

import com.alibaba.druid.util.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SDKApplicationProperties implements Serializable {

	private static final long serialVersionUID = 1L;

	private String group = "macula";
	private String name = "application-name-01";
	private String instance;

	public ApplicationId create() {
		return new ApplicationId(group, name, getInstanceId());
	}

	protected String getInstanceId() {
		if (StringUtils.isEmpty(instance)) {
			try {
				instance = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				instance = UUID.randomUUID().toString();
			}
		}
		return instance;
	}

}
