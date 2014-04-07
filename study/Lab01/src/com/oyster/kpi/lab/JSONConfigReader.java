package com.oyster.kpi.lab;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Map;
import java.util.Set;


public class JSONConfigReader extends ConfigReader {

    public JSONConfigReader(IConfig config) {
        this.config = config;
    }

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