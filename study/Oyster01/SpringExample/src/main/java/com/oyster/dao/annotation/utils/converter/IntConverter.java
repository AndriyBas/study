package com.oyster.dao.annotation.utils.converter;

import com.oyster.dao.annotation.utils.ValueConverter;

/**
 *  converts entity (String <==> Integer)
 */
public class IntConverter implements ValueConverter {

    /**
     *
     * @param value parameter to convert
     * @param <T>
     * @return
     */
    @Override
    public <T> String toString(T value) {
        if (value == null) return "null";
        return Integer.toString((Integer) value);
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
        return (T) new Integer(Integer.parseInt(str));
    }
}
