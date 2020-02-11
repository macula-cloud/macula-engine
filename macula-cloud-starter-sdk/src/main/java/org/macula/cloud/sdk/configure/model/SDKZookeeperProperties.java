package org.macula.cloud.sdk.configure.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SDKZookeeperProperties implements Serializable {

	private static final long serialVersionUID = 1L;

	private String zkServers;

	private int sessionTimeout = 30000;

	private int connectionTimeout = 5000;

	private int baseSleepTimeMs = 1000;

	private int maxRetries = 3;

	private boolean lock;

}
