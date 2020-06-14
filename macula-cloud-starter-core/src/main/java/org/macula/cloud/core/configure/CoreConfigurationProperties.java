package org.macula.cloud.core.configure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.macula.cloud.core.configure.model.ApplicationProperties;
import org.macula.cloud.core.configure.model.CacheProperties;
import org.macula.cloud.core.configure.model.CatProperties;
import org.macula.cloud.core.configure.model.EventProperties;
import org.macula.cloud.core.configure.model.FeignProperties;
import org.macula.cloud.core.configure.model.SecurityProperties;
import org.macula.cloud.core.configure.model.ServiceProperties;
import org.macula.cloud.core.configure.model.ServiceProviderProperties;
import org.macula.cloud.core.configure.model.ZookeeperProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConditionalOnMissingBean
@ConfigurationProperties(value = CoreConfigurationProperties.PREFIX, ignoreUnknownFields = true)
public class CoreConfigurationProperties implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String PREFIX = "macula.cloud";

	@NestedConfigurationProperty
	private ApplicationProperties application = new ApplicationProperties();

	@NestedConfigurationProperty
	private EventProperties event = new EventProperties();

	@NestedConfigurationProperty
	private CacheProperties cache = new CacheProperties();

	@NestedConfigurationProperty
	private ZookeeperProperties zookeeper = new ZookeeperProperties();

	@NestedConfigurationProperty
	private CatProperties cat = new CatProperties();

	@NestedConfigurationProperty
	private FeignProperties feign = new FeignProperties();

	@NestedConfigurationProperty
	private ServiceProperties service = new ServiceProperties();

	@NestedConfigurationProperty
	private SecurityProperties security = new SecurityProperties();

	@NestedConfigurationProperty
	private List<ServiceProviderProperties> providers = new ArrayList<ServiceProviderProperties>();
}
