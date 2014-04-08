package configuration;

import java.io.File;
import java.io.IOException;

import configuration.utils.envreader.EnviromentPropertiesReader;
import configuration.utils.propreader.FilePropertiesReader;
import configuration.utils.sysreader.SystemPropertyReader;
import configuration.utils.xmlreader.XMLReader;

public class ConfigurationFactory {
	public Configuration getConfiguration(Object source) throws IOException{
		
		
		if (source instanceof File){
			
			if (((File)source).getName().endsWith(".properties")){
				
				try {
					return new FilePropertiesReader((File)source).load();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (((File)source).getName().endsWith(".xml")){
				try {
					return new XMLReader((File)source).load();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			throw new IOException();
		}
		
		if (source instanceof String){
			if (source.toString().equals("SYSTEM")){
				Configuration sys = new Configuration();
				try {
					sys = new SystemPropertyReader().load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Configuration env = new Configuration();
				try {
					env = new EnviromentPropertiesReader().load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Configuration result = new Configuration();
				result.appendConfiguration(sys,env);
				return result;
			}
			throw new IOException();
		}
		throw new IOException();
	}
}
