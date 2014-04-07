package com.oyster.kpi.lab;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JSONConfigWriter extends ConfigWriter {

    public JSONConfigWriter(IConfig config) {
        this.config = config;
    }

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