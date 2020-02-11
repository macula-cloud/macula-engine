package org.macula.cloud.sdk.configure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.macula.cloud.sdk.configure.model.SDKApplicationProperties;
import org.macula.cloud.sdk.configure.model.SDKCacheProperties;
import org.macula.cloud.sdk.configure.model.SDKCatProperties;
import org.macula.cloud.sdk.configure.model.SDKEventProperties;
import org.macula.cloud.sdk.configure.model.SDKFeignProperties;
import org.macula.cloud.sdk.configure.model.SDKSecurityProperties;
import org.macula.cloud.sdk.configure.model.SDKServiceProperties;
import org.macula.cloud.sdk.configure.model.SDKServiceProviderProperties;
import org.macula.cloud.sdk.configure.model.SDKZookeeperProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import lombok.Getter;
import lombok.Setter;

@ConditionalOnMissingBean
@ConfigurationProperties(value = "macula.cloud", ignoreUnknownFields = false)
@Getter
@Setter
public class SDKConfigurationProperties implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String PREFIX = "macula.cloud";

	@NestedConfigurationProperty
	private SDKApplicationProperties application = new SDKApplicationProperties();

	@NestedConfigurationProperty
	private SDKEventProperties event = new SDKEventProperties();

	@NestedConfigurationProperty
	private SDKCacheProperties cache = new SDKCacheProperties();

	@NestedConfigurationProperty
	private SDKZookeeperProperties zookeeper = new SDKZookeeperProperties();

	@NestedConfigurationProperty
	private SDKCatProperties cat = new SDKCatProperties();

	@NestedConfigurationProperty
	private SDKFeignProperties feign = new SDKFeignProperties();

	@NestedConfigurationProperty
	private SDKServiceProperties service = new SDKServiceProperties();

	@NestedConfigurationProperty
	private SDKSecurityProperties security = new SDKSecurityProperties();

	@NestedConfigurationProperty
	private List<SDKServiceProviderProperties> providers = new ArrayList<SDKServiceProviderProperties>();
}
