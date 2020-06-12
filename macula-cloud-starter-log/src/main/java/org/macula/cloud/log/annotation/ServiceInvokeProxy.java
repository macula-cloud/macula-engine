package org.macula.cloud.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ServiceInvokeProxy {

	String key() default "";

	String description() default "";

	String source() default "";

	String sourceMethod() default "";

	String sourceMessage() default "";

	String target() default "";

	String targetMethod() default "";

}
