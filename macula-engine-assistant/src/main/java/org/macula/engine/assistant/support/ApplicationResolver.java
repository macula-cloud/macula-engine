package org.macula.engine.assistant.support;

import org.macula.engine.assistant.constants.BaseConstants;

import org.springframework.core.env.Environment;

/**
 * <p>Description: Spring Context Property Resolver </p>
 */
public class ApplicationResolver extends PropertyResolver {

	public static String getApplicationGroup(Environment environment) {
		return PropertyResolver.getProperty(environment, BaseConstants.ITEM_APPLICATION_GROUP);
	}

	public static String getApplicationName(Environment environment) {
		return PropertyResolver.getProperty(environment, BaseConstants.ITEM_SPRING_APPLICATION_NAME);
	}

	public static String getApplicationInstance(Environment environment) {
		return PropertyResolver.getProperty(environment, BaseConstants.ITEM_APPLICATION_INSTANCE);
	}

	public static String getServerPort(Environment environment) {
		return PropertyResolver.getProperty(environment, BaseConstants.ITEM_SERVER_PORT);
	}
}
