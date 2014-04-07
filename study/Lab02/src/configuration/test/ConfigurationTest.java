package configuration.test;

import java.io.File;
import java.io.IOException;

import configuration.Configuration;
import configuration.ConfigurationFactory;

public class ConfigurationTest {

	
	public static void main(String[] args) {
		try {
			Configuration app = 
					new ConfigurationFactory().
					getConfiguration(
							new File("./test/testappconf.properties")
					);
			System.out.println(app);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
