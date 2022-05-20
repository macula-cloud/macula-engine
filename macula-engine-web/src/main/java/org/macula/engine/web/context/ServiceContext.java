package org.macula.engine.web.context;

import org.apache.commons.lang3.ObjectUtils;
import org.macula.engine.assistant.constants.SymbolConstants;
import org.macula.engine.assistant.utils.StringUtils;
import org.macula.engine.web.enums.Architecture;
import org.macula.engine.web.enums.DataAccessStrategy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

/**
 * <p> Spring Boot Service Context Utility </p>
 */
public class ServiceContext {

	private static volatile ServiceContext instance;

	/**
	 * 平台架构类型，默认：MICROSERVICE（分布式架构）
	 */
	private Architecture architecture = Architecture.MICROSERVICE;
	/**
	 * 数据访问策略，默认：REMOTE
	 */
	private DataAccessStrategy dataAccessStrategy = DataAccessStrategy.REMOTE;

	/**
	 * 服务端口号
	 */
	private String port;
	/**
	 * 服务IP地址
	 */
	private String ip;
	/**
	 * 服务地址，格式：ip:port
	 */
	private String address;
	/**
	 * 服务Url，格式：http://ip:port
	 */
	private String url;
	/**
	 * 应用名称，与spring.application.name一致
	 */
	private String applicationName;
	/**
	 * 认证中心服务名称
	 */
	private String uaaServiceName;
	/**
	 * 用户中心服务名称
	 */
	private String upmsServiceName;
	/**
	 * 网关地址
	 */
	private String gatewayAddress;
	/**
	 * 留存一份ApplicationContext
	 */
	private ApplicationContext applicationContext;

	private ServiceContext() {

	}

	public static ServiceContext getInstance() {
		if (ObjectUtils.isEmpty(instance)) {
			synchronized (ServiceContext.class) {
				if (ObjectUtils.isEmpty(instance)) {
					instance = new ServiceContext();
				}
			}
		}

		return instance;
	}

	public Architecture getArchitecture() {
		return architecture;
	}

	public void setArchitecture(Architecture architecture) {
		this.architecture = architecture;
	}

	public DataAccessStrategy getDataAccessStrategy() {
		return dataAccessStrategy;
	}

	public void setDataAccessStrategy(DataAccessStrategy dataAccessStrategy) {
		this.dataAccessStrategy = dataAccessStrategy;
	}

	public boolean isDistributedArchitecture() {
		return this.getArchitecture() == Architecture.MICROSERVICE;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getGatewayAddress() {
		return gatewayAddress;
	}

	public void setGatewayAddress(String gatewayAddress) {
		this.gatewayAddress = gatewayAddress;
	}

	public String getAddress() {
		if (isDistributedArchitecture()) {
			this.address = this.getGatewayAddress() + SymbolConstants.FORWARD_SLASH + this.getApplicationName();
		} else {
			if (StringUtils.isNotBlank(this.ip) && StringUtils.isNotBlank(this.port)) {
				this.address = this.ip + SymbolConstants.COLON + this.port;
			}
		}
		return address;
	}

	public String getUrl() {
		if (StringUtils.isBlank(this.url)) {
			String address = this.getAddress();
			if (StringUtils.isNotBlank(address)) {
				return StringUtils.addressToUri(address);
			}
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getUaaServiceName() {
		return uaaServiceName;
	}

	public void setUaaServiceName(String uaaServiceName) {
		this.uaaServiceName = uaaServiceName;
	}

	public String getUpmsServiceName() {
		return upmsServiceName;
	}

	public void setUpmsServiceName(String upmsServiceName) {
		this.upmsServiceName = upmsServiceName;
	}

	public String getOriginService() {
		return getApplicationName() + SymbolConstants.COLON + getPort();
	}

	public void publishEvent(ApplicationEvent applicationEvent) {
		getApplicationContext().publishEvent(applicationEvent);
	}
}
