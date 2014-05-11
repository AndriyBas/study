package com.oyster.config;


abstract public class ConfigReader {

	public IConfig config;

	abstract public void loadFromFile(String path);

	public void setConfig(IConfig config) {
		this.config = config;
	}

}
