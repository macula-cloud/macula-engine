package org.macula.engine.web.properties;

import lombok.ToString;
import org.macula.engine.web.constants.WebConstants;
import org.macula.engine.web.enums.Architecture;
import org.macula.engine.web.enums.DataAccessStrategy;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Platform Settings Properties </p>
 */
@ToString
@ConfigurationProperties(prefix = WebConstants.PROPERTY_PREFIX_PLATFORM)
public class PlatformProperties {

	/**
	 * 平台架构类型，默认：DISTRIBUTED（分布式架构）
	 */
	private Architecture architecture = Architecture.MICROSERVICE;
	/**
	 * 数据访问策略，默认：
	 */
	private DataAccessStrategy dataAccessStrategy = DataAccessStrategy.REMOTE;

	private Swagger swagger = new Swagger();

	private Debezium debezium = new Debezium();

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

	public Swagger getSwagger() {
		return swagger;
	}

	public void setSwagger(Swagger swagger) {
		this.swagger = swagger;
	}

	public Debezium getDebezium() {
		return debezium;
	}

	public void setDebezium(Debezium debezium) {
		this.debezium = debezium;
	}

	@ToString
	public static class Swagger {

		/**
		 * 是否开启Swagger
		 */
		private Boolean enabled;

		public Boolean getEnabled() {
			return enabled;
		}

		public void setEnabled(Boolean enabled) {
			this.enabled = enabled;
		}
	}

	public static class Debezium {

		/**
		 * 是否开启 Debezium
		 */
		private Boolean enabled = false;

		public Boolean getEnabled() {
			return enabled;
		}

		public void setEnabled(Boolean enabled) {
			this.enabled = enabled;
		}
	}
}
