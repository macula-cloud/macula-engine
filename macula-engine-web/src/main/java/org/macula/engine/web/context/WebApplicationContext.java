
package org.macula.engine.web.context;

import jakarta.annotation.PostConstruct;

import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;
import org.macula.engine.assistant.support.ApplicationResolver;
import org.macula.engine.assistant.utils.StringUtils;
import org.macula.engine.web.properties.EndpointProperties;
import org.macula.engine.web.properties.PlatformProperties;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ApplicationContext;

/**
 * <p>Macula Web Application Context</p>
 */
@Getter
public class WebApplicationContext {

	private final ApplicationContext applicationContext;
	private final PlatformProperties platformProperties;
	private final EndpointProperties endpointProperties;
	private final ServerProperties serverProperties;

	private ServiceContext serviceContext;

	public WebApplicationContext(ApplicationContext applicationContext, PlatformProperties platformProperties, EndpointProperties endpointProperties,
			ServerProperties serverProperties) {
		this.applicationContext = applicationContext;
		this.platformProperties = platformProperties;
		this.endpointProperties = endpointProperties;
		this.serverProperties = serverProperties;
	}

	@PostConstruct
	public void initServiceContext() {
		this.serviceContext.setArchitecture(this.platformProperties.getArchitecture());
		this.serviceContext.setDataAccessStrategy(this.platformProperties.getDataAccessStrategy());
		this.serviceContext.setGatewayAddress(this.endpointProperties.getGatewayServiceUri());
		this.serviceContext.setPort(String.valueOf(this.getPort()));
		this.serviceContext.setIp(getHostAddress());
		this.serviceContext.setApplicationContext(applicationContext);
		this.serviceContext.setApplicationName(ApplicationResolver.getApplicationName(applicationContext.getEnvironment()));
	}

	private String getHostAddress() {
		String address = StringUtils.getHostAddress();
		if (ObjectUtils.isNotEmpty(serverProperties.getAddress())) {
			address = serverProperties.getAddress().getHostAddress();
		}
		return address;
	}

	private Integer getPort() {
		Integer port = serverProperties.getPort();
		if (ObjectUtils.isNotEmpty(port)) {
			return port;
		} else {
			return 8080;
		}
	}

}
