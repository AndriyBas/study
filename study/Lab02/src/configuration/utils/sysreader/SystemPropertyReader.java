package configuration.utils.sysreader;

import java.io.IOException;
import java.util.Properties;


import configuration.Configuration;
import configuration.utils.ConfigurationLoader;

public class SystemPropertyReader implements ConfigurationLoader {
	

	@Override
	public Configuration load() throws IOException {
		Configuration configuration = new Configuration();
		Properties p = System.getProperties();
		Object[] keys = p.keySet().toArray();
		for(Object cur : keys){
			String key = (String) cur;
			configuration.setProperty("sys:"+key, p.getProperty(key));
		}
		return configuration;
	}

}
