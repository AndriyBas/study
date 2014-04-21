package com.oyster.core.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention (value = RetentionPolicy.RUNTIME)
@Target (value = ElementType.TYPE)

public @interface PARAMETER{
	public String key();
	public Class<?> type() default Object.class;
	public boolean optional() default false;	
}
