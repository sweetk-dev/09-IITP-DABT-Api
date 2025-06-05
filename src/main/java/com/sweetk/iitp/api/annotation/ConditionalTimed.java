package com.sweetk.iitp.api.annotation;

import io.micrometer.core.annotation.Timed;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Timed
@ConditionalOnProperty(name = "management.metrics.timers.enabled", havingValue = "true")
public @interface ConditionalTimed {
    String value() default "";
    String description() default "";
} 