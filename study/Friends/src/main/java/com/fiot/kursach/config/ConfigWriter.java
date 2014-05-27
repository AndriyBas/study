package com.fiot.kursach.config;


/**
 * Абстрактний клас для уніфікованого доступу до об’єкту конфігурації
 */
public abstract class ConfigWriter {

	public IConfig config;

    /**
     * записує конфігураіцю у JSON - файл
     * @param path шлях до файлу
     */
	abstract public void save(String path);

    /**
     * встановлює об’єкт конфігурації
     * @param config об’єкт конфігурації
     */
	public void setConfig(IConfig config) {
		this.config = config;
	}

}