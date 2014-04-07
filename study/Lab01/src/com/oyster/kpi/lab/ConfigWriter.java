package com.oyster.kpi.lab;


public abstract class ConfigWriter {

	public IConfig config;

	abstract public void save(String path);

	public void setConfig(IConfig config) {
		this.config = config;
	}

}