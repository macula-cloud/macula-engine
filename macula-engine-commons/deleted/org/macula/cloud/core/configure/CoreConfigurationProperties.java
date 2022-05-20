package org.macula.cloud.core.configure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.macula.cloud.core.configure.model.SecurityProperties;
import org.macula.cloud.core.configure.model.ServiceProviderProperties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Getter
@Setter
@ConditionalOnMissingBean
@ConfigurationProperties(value = CoreConfigurationProperties.PREFIX, ignoreUnknownFields = true)
public class CoreConfigurationProperties implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String PREFIX = "macula.cloud";

	@NestedConfigurationProperty
	private SecurityProperties security = new SecurityProperties();

	@NestedConfigurationProperty
	private List<ServiceProviderProperties> providers = new ArrayList<ServiceProviderProperties>();
}
