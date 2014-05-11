package com.oyster.core.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)

/**
 * анотація для параметра команди
 */
public @interface PARAMETER {
    /**
     * повертає ключ параметра
     *
     * @return ключ параметра
     */
    public String key();

    /**
     * повертає тип параметра
     *
     * @return тип параметра
     */
    public Class<?> type() default Object.class;

    /**
     * визначає, чи є даний параметр обов’язковим
     *
     * @return true - якщо необов’язковий, false - обов’язковий
     */
    public boolean optional() default false;
}
