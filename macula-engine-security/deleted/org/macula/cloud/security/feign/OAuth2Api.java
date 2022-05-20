package org.macula.cloud.security.feign;

public @interface OAuth2Api {

	String clientId() default "";
}
