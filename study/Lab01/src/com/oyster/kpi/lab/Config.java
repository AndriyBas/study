package com.oyster.kpi.lab;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Config implements IConfig {
    private Map<String, Object> map;

    public Config() {
        map = new HashMap<String, Object>();
    }

    public void setValue(String key, Object value) {
        map.put(key, value);
    }

    public Object getValue(String key) {
        return map.get(key);
    }

    public String output() {
        return map.toString();
    }

    @Override
    public Set<String> getAllKeys() {
        return map.keySet();
    }
}
