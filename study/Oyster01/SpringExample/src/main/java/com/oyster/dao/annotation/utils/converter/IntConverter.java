package com.oyster.dao.annotation.utils.converter;

import com.oyster.dao.annotation.utils.ValueConverter;

/**
 *  конвертує сутність (String <==> Integer)
 */
public class IntConverter implements ValueConverter {

    /**
     *
     * @param value параметр для конвертації
     * @param <T>
     * @return
     */
    @Override
    public <T> String toString(T value) {
        if (value == null) return "null";

        // костиль (for Spring JDBC)
        if (value instanceof Long) {
            return value.toString();
        }

        return Integer.toString((Integer) value);
    }

    /**
     *
     * @param str String для конвертування
     * @param <T>
     * @return
     */
    @Override
    public <T> T toValue(String str) {
        if (str.equals("null")) return null;
        return (T) new Integer(Integer.parseInt(str));
    }
}
