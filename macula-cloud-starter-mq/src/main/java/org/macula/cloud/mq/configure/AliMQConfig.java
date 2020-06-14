package org.macula.cloud.mq.configure;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.aliyun.openservices.ons.api.PropertyKeyConst;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "macula.cloud.alimq")
public class AliMQConfig {

	/**
	* MQ 接入参数 accessKey
	*/
	private String accessKey;

	/**
	 * MQ 接入参数 secretKey
	 */
	private String secretKey;

	/**
	 * MQ 的接入域名
	 */
	private String onsAddr;

	/**
	 * 分组ID
	 */
	private String groupId;

	@SuppressWarnings("deprecation")
	public Properties getProperties() {
		Properties properties = new Properties();
		properties.put(PropertyKeyConst.AccessKey, accessKey);
		properties.put(PropertyKeyConst.SecretKey, secretKey);
		properties.put(PropertyKeyConst.ONSAddr, onsAddr);
		properties.put(PropertyKeyConst.ConsumerId, groupId);
		properties.put(PropertyKeyConst.ProducerId, groupId);
		return properties;
	}
}
