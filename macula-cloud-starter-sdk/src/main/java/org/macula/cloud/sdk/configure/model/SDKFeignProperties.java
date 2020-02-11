package org.macula.cloud.sdk.configure.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SDKFeignProperties implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean oauth2;

}
