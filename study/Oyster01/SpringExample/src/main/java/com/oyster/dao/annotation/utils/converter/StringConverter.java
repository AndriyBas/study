package com.oyster.dao.annotation.utils.converter;

import com.oyster.dao.annotation.utils.ValueConverter;

/**
 * конвертує сутність (String <==> String)
 */
public class StringConverter implements ValueConverter {

    /**
     * @param value параметр для конвертування
     * @param <T>
     * @return
     */
    @Override
    public <T> String toString(T value) {
        if (value == null) return null;
        return "\"" + ((Object) value).toString() + "\"";
    }

    /**
     * @param str String для конвертування
     * @param <T>
     * @return
     */
    @Override
    public <T> T toValue(String str) {
        if (str == null) return null;
        if (str.equals("null")) return null;
        if (str.equals("\"\"")) return (T) "";
        if (str.equals("")) return (T) "";

        str = str.substring(1, str.length() - 1);
        return (T) str;
    }
}
