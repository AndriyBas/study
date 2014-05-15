package com.oyster.dao.annotation.utils;

/**
 * інтерфейс для класів, які пертворюють певні поля типу Т в String і навпаки
 */
public interface ValueConverter {
    /**
     * конвертує екземпляр типу Т в String
     *
     * @param value параметр для конвертування
     * @param <T>  тип параметру
     * @return параметр конвертований в String
     */
    public <T> String toString(T value);

    /**
     * конвертує String до екземпляру типу Т
     *
     * @param str String для конвертування
     * @param <T> тип параметру
     * @return об’єкт типу Т
     */
    public <T> T toValue(String str);
}
