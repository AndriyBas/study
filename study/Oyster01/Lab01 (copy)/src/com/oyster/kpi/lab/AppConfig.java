package com.oyster.kpi.lab;


import java.util.List;

public class AppConfig implements IConfig {

	public IConfig AppConfig;

	private static Config config;
	private static com.oyster.kpi.lab.AppConfig appConfig;

	private AppConfig() {
		config = new Config();
	}

	public static com.oyster.kpi.lab.AppConfig getInstance() {
		if (appConfig == null)
			appConfig = new com.oyster.kpi.lab.AppConfig();
		return appConfig;
	}

	@Override
	public void setValue(String key, String value) {
		config.setValue(key, value);
	}

	@Override
	public String getValue(String key) {
		return config.getValue(key);
	}

	public String out() {
		return config.output();
	}

	@Override
	public List<String> getAllKeys() {
		return config.getAllKeys();
	}

}
