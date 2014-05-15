package com.oyster.core.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)

/**
 * анотація для команд
 */
public @interface COMMAND {
    /**
     * повертає ключ команди
     *
     * @return ключ команди
     */
    public String key();
}
