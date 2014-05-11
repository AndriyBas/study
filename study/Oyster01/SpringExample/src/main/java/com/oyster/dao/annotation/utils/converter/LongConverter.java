package com.oyster.dao.annotation.utils.converter;

import com.oyster.dao.annotation.utils.ValueConverter;

import java.math.BigInteger;

/**
 * конвертує сутність (String <==> Long)
 */
public class LongConverter implements ValueConverter {

    /**
     * @param value параметр для конвертування
     * @param <T>
     * @return
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
     * @param str Long для конвертування
     * @param <T>
     * @return
     */
    @Override
    public <T> T toValue(String str) {
        if (str.equals("null")) return null;
        return (T) new Long(Long.parseLong(str));
    }
}
