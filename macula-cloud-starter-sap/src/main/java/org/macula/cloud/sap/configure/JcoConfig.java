package org.macula.cloud.sap.configure;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "macula.cloud.sap")
public class JcoConfig {

	private String name;
	private List<String> packages;
	private Map<String, String> props;

}
