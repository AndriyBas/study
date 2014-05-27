package com.fiot.kursach.config.impl;


import com.fiot.kursach.config.ConfigReader;
import com.fiot.kursach.config.IConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.util.Map;
import java.util.Set;


/**
 * клас реалізує зчитування файлу конфігурації із JSON - файлу
 */
public class JSONConfigReader extends ConfigReader {

    /**
     * Конструктор
     * @param config об’єкт конфігурації
     */
    public JSONConfigReader(IConfig config) {
        this.config = config;
    }

    /**
     * завантажує зовнішній файл конфігурації
     * @param path шлях до файлу
     */
    @Override
    public void loadFromFile(String path) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            // read JSON from a file
            Map<String, Object> map = mapper.readValue(
                    new File(path),
                    new TypeReference<Map<String, Object>>() {
                    }
            );

            Set<String> set = map.keySet();
            for (String key : set) {
                config.setValue(key, map.get(key));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}