package configuration.utils.propreader.cmd;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import configuration.Configuration;
import configuration.ConfigurationFactory;
import configuration.utils.propreader.FilePropertiesReader;
import configuration.utils.propreader.LineEvent;

public class SetPrefixCMD implements Observer {
	@Override
	public void update(Observable o, Object arg) {
		LineEvent event = (LineEvent) arg;
		String line = event.getLine().trim();
		Configuration conf = event.getConfiguration();
		if (!line.startsWith("@prefix")){return;}
		String prefix = line.split("=")[1].trim();
		((FilePropertiesReader)o).setPrefix(prefix);
	}

}
