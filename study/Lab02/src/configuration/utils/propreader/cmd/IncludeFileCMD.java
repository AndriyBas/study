package configuration.utils.propreader.cmd;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import configuration.Configuration;
import configuration.ConfigurationFactory;
import configuration.utils.propreader.FilePropertiesReader;
import configuration.utils.propreader.LineEvent;

public class IncludeFileCMD implements Observer {
	private boolean complete = false;
	@Override
	public void update(Observable o, Object arg) {
		
		if (complete){return;}
		
		LineEvent event = (LineEvent) arg;
		String line = event.getLine().trim();
		Configuration conf = event.getConfiguration();
		if (!line.startsWith("@include")){return;}
		String fileName = line.split("=")[1].trim();
		
		Configuration incConf;
		try {
			if (fileName.equals("SYSTEM")){
				incConf = new ConfigurationFactory().getConfiguration("SYSTEM");
			}else{
				incConf = new ConfigurationFactory().getConfiguration(new File(fileName));
			}
			conf.appendConfiguration(incConf);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
	}

}
