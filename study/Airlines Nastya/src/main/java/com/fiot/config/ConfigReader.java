package com.fiot.config;


/**
 * Абстрактний клас для уніфікованого доступу до об’єкту конфігурації
 */
abstract public class ConfigReader {

	public IConfig config;

    /**
     * завантажує зовнішній файл конфігурації
     * @param path шлях до файлу
     */
	abstract public void loadFromFile(String path);

    /**
     * встановлює об’єкт конфігурації
     * @param config об’єкт конфігурації
     */
	public void setConfig(IConfig config) {
		this.config = config;
	}

}
