package com.fiot.kursach.dao.annotation.utils.converter;

import com.fiot.kursach.dao.annotation.utils.ValueConverter;

import java.util.UUID;

/**
 * конвертує сутність (String <==> UUID)
 */
public class UUIDConverter implements ValueConverter {

    /**
     * перетворює UUID в String
     *
     * @param value параметр для конвертації
     * @param <T>   тип параметру
     * @return параметр як стрічку
     */
    @Override
    public <T> String toString(T value) {
        if (value == null) return null;
        return "\"" + ((Object) value).toString() + "\"";
    }

    /**
     * перетворює String в UUID
     *
     * @param str String для конвертування
     * @param <T> тип параметру
     * @return стрічку як параметр
     */
    @Override
    public <T> T toValue(String str) {
        if (str == null) return null;
        if (str.equals("null")) return null;
        str = str.substring(1, str.length() - 1);
        return (T) UUID.fromString(str);
    }
}
