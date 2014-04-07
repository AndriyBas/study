package configuration.utils.xmlreader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import configuration.Configuration;
import configuration.utils.ConfigurationLoader;

public class XMLReader extends DefaultHandler implements ConfigurationLoader {
	
	private File source;
	private Stack<String> key = new Stack<String>();
	private Configuration configuration = new Configuration();

	public XMLReader(File source) {
		super();
		this.source = source;
	}

	
	public File getSource() {
		return source;
	}


	public void setSource(File source) {
		this.source = source;
	}


	@Override
	public Configuration load() throws IOException {
		
		
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = parserFactory.newSAXParser();
			InputSource isource = new InputSource(new FileInputStream(source));
			parser.parse(isource, this);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		return configuration;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
			key.push(qName);
		
	}	
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		key.pop();
	}	
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		String value = new String(ch,start,length).trim();
		
		if (value.length()== 0){return;}
		
		String[] keyseq = key.toArray(new String[0]); 
		String keyString = "";
		for(String curr : keyseq){
			keyString += curr + "."; 
		}
		keyString = keyString.substring(0, keyString.length()-1);
		
		if (configuration.containsKey(keyString)){
			configuration.setProperty(keyString, configuration.getProperty(keyString)+","+value);
		}else{
			configuration.setProperty(keyString, value);
		}
		
	}


}
