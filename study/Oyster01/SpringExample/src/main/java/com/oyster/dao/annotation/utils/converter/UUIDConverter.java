package com.oyster.dao.annotation.utils.converter;

import com.oyster.dao.annotation.utils.ValueConverter;

import java.util.UUID;

/**
 * конвертує сутність (String <==> UUID)
 */
public class UUIDConverter implements ValueConverter {

    /**
     * @param value параметр для конвертації
     * @param <T>
     * @return
     */
    @Override
    public <T> String toString(T value) {
        if (value == null) return null;
        return "\"" + ((Object) value).toString() + "\"";
    }

    /**
     * @param str String для конвертації
     * @param <T>
     * @return
     */
    @Override
    public <T> T toValue(String str) {
        if (str == null) return null;
        if (str.equals("null")) return null;
		str = str.substring(1, str.length()-1);
        return (T) UUID.fromString(str);
    }
}
