package com.oyster.core.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)

/**
 * анотація для контексту, що передається команді
 */
public @interface CONTEXT {
    /**
     * повертає список параметрів
     *
     * @return список параметрів
     */
    PARAMETER[] list();
}
