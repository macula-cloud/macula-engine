package org.macula.engine.login;

import org.macula.engine.assistant.constants.BaseConstants;

public interface LoginConstants extends BaseConstants {

	String PROPERTY_PREFIX_OAUTH2 = PROPERTY_PREFIX_MACULA + ".oauth2";
	String PROPERTY_PREFIX_LOGIN = PROPERTY_PREFIX_MACULA + ".login";

	String REGION_OAUTH2_AUTHORIZATION = AREA_PREFIX + "oauth2:authorization";
	String REGION_OAUTH2_AUTHORIZATION_CONSENT = AREA_PREFIX + "oauth2:authorization:consent";
	String REGION_OAUTH2_REGISTERED_CLIENT = AREA_PREFIX + "oauth2:registered:client";
	String REGION_OAUTH2_APPLICATION = AREA_PREFIX + "oauth2:application";
	String REGION_OAUTH2_AUTHORITY = AREA_PREFIX + "oauth2:authority";
	String REGION_OAUTH2_SCOPE = AREA_PREFIX + "oauth2:scope";
	String REGION_OAUTH2_APPLICATION_SCOPE = AREA_PREFIX + "oauth2:application:scope";

}
