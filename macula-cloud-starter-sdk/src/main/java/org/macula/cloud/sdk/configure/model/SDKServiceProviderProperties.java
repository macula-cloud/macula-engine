package org.macula.cloud.sdk.configure.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SDKServiceProviderProperties implements Serializable {

	private static final long serialVersionUID = 1L;
	private String appId;

	private String appKey;

	private String appSecret;
}
