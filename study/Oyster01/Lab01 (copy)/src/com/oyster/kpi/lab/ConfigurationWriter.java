package com.oyster.kpi.lab;


import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public abstract class ConfigurationWriter {

    public IConfig config;

    abstract public void save(String path) throws IOException, ParserConfigurationException;

    public void setConfig(IConfig config) {
        this.config = config;
    }

}