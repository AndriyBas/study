package configuration.utils.propreader.cmd;

import java.util.Observable;
import java.util.Observer;

import configuration.Configuration;
import configuration.utils.propreader.FilePropertiesReader;
import configuration.utils.propreader.LineEvent;

public class GetPropertyCMD implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		
		LineEvent event = (LineEvent) arg;
		String line = event.getLine().trim();
		Configuration conf = event.getConfiguration();
		
		if (line.startsWith("#")){return;}
		if (line.startsWith("@")){return;}
		if (line.equals("")){return;}
		if (!line.contains("=")){return;}
//		System.out.println("PROPERTY");
//		System.out.println(line);
		
		String[] buf = line.split("=");
		String key = buf[0].trim();
		String value = buf[1].trim();
		String prefix = ((FilePropertiesReader)o).getPrefix();
		if (!prefix.equals("")){
			key = prefix+":"+key;
		}
		
		if (conf.containsKey(key)){
			conf.setProperty(key, conf.getProperty(key)+","+value);
		}else{
			conf.setProperty(key, value);
		}
	}

}
