package com.oyster.kpi.lab;


import java.util.List;

public interface IConfig {

    public void setValue(String key, String value);

    public String getValue(String key);

    public List<String> getAllKeys();

}