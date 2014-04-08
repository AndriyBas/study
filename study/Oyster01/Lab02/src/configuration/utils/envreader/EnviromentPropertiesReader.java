package configuration.utils.envreader;

import java.io.IOException;
import java.util.HashMap;
import configuration.Configuration;
import configuration.utils.ConfigurationLoader;

public class EnviromentPropertiesReader implements ConfigurationLoader {

	@Override
	public Configuration load() throws IOException {
		Configuration configuration = new Configuration();
		HashMap<String, Object> p = new HashMap<String, Object>(System.getenv());
		Object[] keys = p.keySet().toArray();
		for(Object cur : keys){
			String key = (String) cur;
			configuration.setProperty("env:"+key, (String)p.get(key));
		}
		return configuration;
	}

}
