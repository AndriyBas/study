package com.oyster.config;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AppConfig implements IConfig {

    private Map<String, Object> map;

    private static AppConfig singletonAppConfig;

    private AppConfig() {
        map = new HashMap<String, Object>();
    }

    public static AppConfig getInstance() {
        if (singletonAppConfig == null)
            synchronized (AppConfig.class) {
                if (singletonAppConfig == null) {
                    singletonAppConfig = new AppConfig();
                }
            }
        return singletonAppConfig;
    }

    @Override
    public void setValue(String key, Object value) {
        map.put(key, value);
    }

    @Override
    public Object getValue(String key) {
        return map.get(key);
    }

    public String out() {
        return map.toString();
    }

    @Override
    public Set<String> getAllKeys() {
        return map.keySet();
    }


}
