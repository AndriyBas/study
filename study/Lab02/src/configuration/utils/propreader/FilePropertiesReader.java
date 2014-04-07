package configuration.utils.propreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Observable;

import configuration.Configuration;
import configuration.utils.ConfigurationLoader;
import configuration.utils.propreader.cmd.ChangeEncodingCMD;
import configuration.utils.propreader.cmd.GetPropertyCMD;
import configuration.utils.propreader.cmd.IncludeFileCMD;
import configuration.utils.propreader.cmd.SetPrefixCMD;

public class FilePropertiesReader extends Observable implements ConfigurationLoader{
	
	
	
	private BufferedReader reader;
	private File source;
	private String prefix = "";

	
	public File getSource() {
		return source;
	}


	public String getPrefix() {
		return prefix;
	}


	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


	public BufferedReader getReader() {
		return reader;
	}


	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	
	
	public FilePropertiesReader(File source) {
		this.source = source;
		this.addObserver(new GetPropertyCMD());
		this.addObserver(new ChangeEncodingCMD());
		this.addObserver(new IncludeFileCMD());
		this.addObserver(new SetPrefixCMD());
	}

	 	
	


	public Configuration load() throws IOException{
		
		Configuration configuration = new Configuration(); 
		
		InputStream in = new FileInputStream( source);
		reader = new BufferedReader(new InputStreamReader(in));
			
	       while (true)
	        {
	            String line = reader.readLine();
	            
	            if (line == null){
	            	reader.close();
	            	this.deleteObservers();
	            	return configuration;
	            }
	            this.setChanged();
	            this.notifyObservers(new LineEvent(configuration, line));
	        }
	 }

}
