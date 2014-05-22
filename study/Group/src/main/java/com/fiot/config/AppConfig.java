package com.fiot.config;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Клас конфігурації, реалізує паттерн Singleton
 */
public class AppConfig implements IConfig {

    private Map<String, Object> map;

    private static AppConfig singletonAppConfig;

    /**
     * Конструктор за замовчуванням
     */
    private AppConfig() {
        map = new HashMap<String, Object>();
    }

    /**
     * Метод для отримання об’єкту класу конфігурацій,
     * якщо такого немає, створює його,
     * використовує метод подвійної перевірки, тому
     * може безпечно використовуватись у багатопоточних застосунках
     * @return об’єкт конфігурації
     */
    public static AppConfig getInstance() {
        if (singletonAppConfig == null)
            synchronized (AppConfig.class) {
                if (singletonAppConfig == null) {
                    singletonAppConfig = new AppConfig();
                }
            }
        return singletonAppConfig;
    }

    /**
     * встановлює значення для поля із ключем key
     *
     * @param key ключ поля
     * @param value значення поля
     */
    @Override
    public void setValue(String key, Object value) {
        map.put(key, value);
    }

    /**
     * дістає значення із ключем  key
     * @param key ключ поля
     * @return значення поля
     */
    @Override
    public Object getValue(String key) {
        return map.get(key);
    }

    /**
     * Виводить усі значення конфігурацій
     * @return карта значень конфігурації
     */
    public String out() {
        return map.toString();
    }

    @Override
    public Set<String> getAllKeys() {
        return map.keySet();
    }


}
