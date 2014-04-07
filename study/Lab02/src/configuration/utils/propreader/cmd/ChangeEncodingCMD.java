package configuration.utils.propreader.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

import configuration.utils.propreader.FilePropertiesReader;
import configuration.utils.propreader.LineEvent;

public class ChangeEncodingCMD implements Observer {
	
	@Override
	public void update(Observable o, Object arg) {
		
		LineEvent event = (LineEvent) arg;
		String line = event.getLine().trim();
		if (!line.startsWith("@encoding")){return;}
		String encoding = line.split("=")[1].trim();
		
		
		
		
		FilePropertiesReader scanner = (FilePropertiesReader) o;
		BufferedReader reader = scanner.getReader();
		
		try {
			
			reader.close();
			InputStream in = new FileInputStream( scanner.getSource());
			reader = new BufferedReader(new InputStreamReader(in, encoding));
			scanner.setReader(reader);
			
			
			
			event.getConfiguration().clear();
			
			scanner.deleteObserver(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
