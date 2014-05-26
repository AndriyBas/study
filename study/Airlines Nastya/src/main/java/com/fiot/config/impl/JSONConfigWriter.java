package com.fiot.config.impl;


import com.fiot.config.ConfigWriter;
import com.fiot.config.IConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 *  клас реалізує записування файлу конфігурації до JSON - файлу
 */
public class JSONConfigWriter extends ConfigWriter {

    /**
     * конструктор
     * @param config об’єкт конфігурацій
     */
    public JSONConfigWriter(IConfig config) {
        this.config = config;
    }

    /**
     * записує конфігураіцю у JSON - файл
     * @param path шлях до файлу
     */
    @Override
    public void save(String path) {

        ObjectMapper mapper = new ObjectMapper();

        try {

            Set<String> set = config.getAllKeys();
            Map<String, Object> map = new HashMap<String, Object>();

            for (String key : set) {
                map.put(key, config.getValue(key));
            }

            // write JSON to a file
            mapper.writeValue(new File(path), map);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}