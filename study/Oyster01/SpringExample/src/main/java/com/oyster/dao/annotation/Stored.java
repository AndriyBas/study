package com.oyster.dao.annotation;

import com.oyster.dao.annotation.utils.converter.StringConverter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Annotation defines the columns where fields are stored and converters
 * for them, can also be used for class declaration
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Stored {
    /**
     * @return name of column/table where entity is stored
     */
    public String name();

    /**
     * @return ValueConverter for current entity
     */
    public Class converter() default StringConverter.class;
}
