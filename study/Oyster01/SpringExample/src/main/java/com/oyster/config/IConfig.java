package com.oyster.config;


import java.util.Set;

public interface IConfig {

    public void setValue(String key, Object value);

    public Object getValue(String key);

    public Set<String> getAllKeys();
}