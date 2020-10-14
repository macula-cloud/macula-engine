package org.macula.cloud.sap.configure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "macula.cloud.sap")
public class JcoConfig {

	private String name;
	private List<String> packages = new ArrayList<String>();
	private Map<String, String> props = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);

}
