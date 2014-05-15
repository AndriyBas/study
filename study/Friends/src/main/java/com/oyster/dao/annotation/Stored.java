package com.oyster.dao.annotation;

import com.oyster.dao.annotation.utils.converter.StringConverter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * анотація визначає ключ поля, за яким зберігається поле, а також ковертер для нього,
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Stored {
    /**
     * @return name ключ поля, за яким параметр зберігається
     */
    public String name();

    /**
     * повертає ValueConverter для даної сутності
     *
     * @return ValueConverter для даної сутності
     */
    public Class converter() default StringConverter.class;
}
