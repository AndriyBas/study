package com.fiot.kursach.config;


import java.util.Set;

/**
 * Інтерфейс для всіх об’єктів конфігурації
 * декларує методи для отримання та запису полів конфігурації
 */
public interface IConfig {

    /**
     * встановлює значення для поля із ключем key
     *
     * @param key ключ поля
     * @param value значення поля
     */
    public void setValue(String key, Object value);

    /**
     * дістає значення із ключем  key
     * @param key ключ поля
     * @return значення поля
     */
    public Object getValue(String key);

    /**
     * повертає усі ключі із карти конфігурації прешого рівня
     * @return усі ключі першого рівня
     */
    public Set<String> getAllKeys();
}