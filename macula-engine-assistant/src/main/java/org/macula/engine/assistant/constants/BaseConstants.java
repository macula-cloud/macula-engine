package org.macula.engine.assistant.constants;

/**
 * <p>Base Constants </p>
 */
public interface BaseConstants {

	String NONE = "none";
	String DEFAULT_TREE_ROOT_ID = "0";

	/* ---------- 配置属性通用常量 ---------- */

	String PROPERTY_ENABLED = ".enabled";

	String PROPERTY_PREFIX_SPRING = "spring";
	String PROPERTY_PREFIX_MACULA = "macula";

	/* ---------- 应用信息通用常量 ---------- */
	String PROPERTY_PREFIX_APPLICATION = PROPERTY_PREFIX_SPRING + ".application";
	String ITEM_APPLICATION_GROUP = PROPERTY_PREFIX_APPLICATION + ".group";
	String ITEM_APPLICATION_NAME = PROPERTY_PREFIX_APPLICATION + ".name";
	String ITEM_APPLICATION_INSTANCE = PROPERTY_PREFIX_APPLICATION + ".instance";

	String PROPERTY_SPRING_CLOUD = PROPERTY_PREFIX_SPRING + ".cloud";
	String PROPERTY_SPRING_REDIS = PROPERTY_PREFIX_SPRING + ".redis";

	String ANNOTATION_PREFIX = "${";
	String ANNOTATION_SUFFIX = "}";

	/* ---------- Macula 自定义配置属性 ---------- */

	String PROPERTY_PREFIX_PLATFORM = PROPERTY_PREFIX_MACULA + ".platform";
	String PROPERTY_PREFIX_SECURITY = PROPERTY_PREFIX_MACULA + ".security";
	String PROPERTY_PREFIX_REST = PROPERTY_PREFIX_MACULA + ".rest";
	String PROPERTY_PREFIX_SWAGGER = PROPERTY_PREFIX_MACULA + ".swagger";

	String ITEM_SWAGGER_ENABLED = PROPERTY_PREFIX_SWAGGER + PROPERTY_ENABLED;

	/* ---------- Spring 家族配置属性 ---------- */

	String ITEM_SPRING_APPLICATION_NAME = "spring.application.name";
	String ITEM_SERVER_PORT = "server.port";

	String ANNOTATION_APPLICATION_NAME = ANNOTATION_PREFIX + ITEM_SPRING_APPLICATION_NAME + ANNOTATION_SUFFIX;

	/* ---------- 通用缓存常量 ---------- */

	String CACHE_PREFIX = "cache:";

	String CACHE_SIMPLE_BASE_PREFIX = CACHE_PREFIX + "simple:";
	String CACHE_TOKEN_BASE_PREFIX = CACHE_PREFIX + "token:";

	String AREA_PREFIX = "data:upms:";

	/* ---------- Oauth2 和 Security 通用缓存常量 ---------- */

	/**
	 * Oauth2 模式类型
	 */
	String AUTHORIZATION_CODE = "authorization_code";
	String IMPLICIT = "implicit";
	String PASSWORD = "password";
	String CLIENT_CREDENTIALS = "client_credentials";
	String REFRESH_TOKEN = "refresh_token";
	String SOCIAL_CREDENTIALS = "social_credentials";

	String DEFAULT_AUTHORIZATION_ENDPOINT = "/oauth2/authorize";
	String DEFAULT_TOKEN_ENDPOINT = "/oauth2/token";
	String DEFAULT_JWK_SET_ENDPOINT = "/oauth2/jwks";
	String DEFAULT_TOKEN_REVOCATION_ENDPOINT = "/oauth2/revoke";
	String DEFAULT_TOKEN_INTROSPECTION_ENDPOINT = "/oauth2/introspect";
	String DEFAULT_OIDC_CLIENT_REGISTRATION_ENDPOINT = "/connect/register";
	String DEFAULT_OIDC_USER_INFO_ENDPOINT = "/userinfo";

	String OPEN_API_SECURITY_SCHEME_BEARER_NAME = "MACULA_AUTH";

	String BEARER_TYPE = "Bearer";
	String BEARER_TOKEN = BEARER_TYPE + SymbolConstants.SPACE;
	String BASIC_TYPE = "Basic";
	String BASIC_TOKEN = BASIC_TYPE + SymbolConstants.SPACE;
	String ROLE_PREFIX = "ROLE_";
	String AUTHORITY_PREFIX = "OP_";

	String OPEN_ID = "openid";
	String LICENSE = "license";

	String AUTHORITIES = "authorities";
}
