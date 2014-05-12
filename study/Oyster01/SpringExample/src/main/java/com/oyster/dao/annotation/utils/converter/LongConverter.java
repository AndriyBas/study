package com.oyster.dao.annotation.utils.converter;

import com.oyster.dao.annotation.utils.ValueConverter;

import java.math.BigInteger;

/**
 * конвертує сутність (String <==> Long)
 */
public class LongConverter implements ValueConverter {

    /**
     * перетворює Long в String
     * @param value параметр для конвертації
     * @param <T> тип параметру
     * @return параметр як стрічку
     */
    @Override
    public <T> String toString(T value) {
        if (value == null) return "null";

        // костиль (for Spring JDBC)
        if (value instanceof BigInteger) {
            return value.toString();
        }

        return Long.toString((Long) value);
    }

    /**
     * перетворює String в Long
     *
     * @param str String для конвертування
     * @param <T> тип параметру
     * @return стрічку як параметр
     */
    @Override
    public <T> T toValue(String str) {
        if (str.equals("null")) return null;
        return (T) new Long(Long.parseLong(str));
    }
}
