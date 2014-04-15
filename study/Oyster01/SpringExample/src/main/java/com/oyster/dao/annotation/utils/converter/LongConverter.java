package com.oyster.dao.annotation.utils.converter;

import com.oyster.dao.annotation.utils.ValueConverter;

/**
 *  converts entity (String <==> Long)
 */
public class LongConverter implements ValueConverter {

    /**
     *
     * @param value parameter to convert
     * @param <T>
     * @return
     */
    @Override
    public <T> String toString(T value) {
        if (value == null) return "null";
        return Long.toString((Long) value);
    }

    /**
     *
     * @param str String to convert
     * @param <T>
     * @return
     */
    @Override
    public <T> T toValue(String str) {
        if (str.equals("null")) return null;
        return (T) new Long(Long.parseLong(str));
    }
}
