package com.oyster.dao.annotation.utils;

/**
 * interface for classes that convert particular field of type T to String
 * and vice versa
 */
public interface ValueConverter {
    /**
     * convert instance of type T to String
     *
     * @param value parameter to convert
     * @param <T>   type of parameter
     * @return parameter converted to String
     */
    public <T> String toString(T value);

    /**
     * convert String to the instance of type T
     *
     * @param str String to convert
     * @param <T> type of parameter
     * @return object of type T
     */
    public <T> T toValue(String str);
}
