package org.macula.engine.j2cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.macula.engine.assistant.configure.AssistantAutoConfiguration;
import org.macula.engine.j2cache.configure.J2CacheAutoConfiguration;

import org.springframework.context.annotation.Import;

/**
 * <p>Enable Redis  Two Level Cache</p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({
		J2CacheAutoConfiguration.class,
		AssistantAutoConfiguration.class })
public @interface EnableJ2Cache {
}
