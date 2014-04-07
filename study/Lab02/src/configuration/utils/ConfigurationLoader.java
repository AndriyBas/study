package configuration.utils;

import java.io.IOException;

import configuration.Configuration;

public interface ConfigurationLoader {
	public Configuration load() throws IOException;
}
