package com.oyster.kpi.lab;


import java.util.Set;

public class AppConfig implements IConfig {

    private static Config config;

    private AppConfig() {
        config = new Config();
    }

    public static Config getInstance() {
        if (config == null)
            config = new Config();
        return config;
    }

    @Override
    public void setValue(String key, Object value) {
        config.setValue(key, value);
    }

    @Override
    public Object getValue(String key) {
        return config.getValue(key);
    }

    public String out() {
        return config.output();
    }

    @Override
    public Set<String> getAllKeys() {
        return config.getAllKeys();
    }


}
