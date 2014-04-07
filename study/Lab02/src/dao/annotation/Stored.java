package dao.annotation;

import dao.annotation.utils.converter.StringConverter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface Stored {
    public String name();

    public Class converter() default StringConverter.class;
}
