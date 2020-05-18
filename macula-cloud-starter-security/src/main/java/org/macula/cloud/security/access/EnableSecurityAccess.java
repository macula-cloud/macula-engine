package org.macula.cloud.security.access;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.macula.cloud.security.feign.EnableOAuth2ClientFeign;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableWebSecurity
@EnableOAuth2ClientFeign
@Import({ SecurityAccessAutoConfiguration.class })
public @interface EnableSecurityAccess {
}
