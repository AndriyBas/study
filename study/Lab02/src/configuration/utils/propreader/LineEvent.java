package configuration.utils.propreader;

import configuration.Configuration;

public class LineEvent {
	private Configuration configuration;
	private String line;
	
	
	public LineEvent(Configuration configuration, String line) {
		super();
		this.configuration = configuration;
		this.line = line;
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	
	@Override
	public String toString() {
		return "LineEvent[" + hashCode() + "]: [configuration="
				+ configuration + ", line=" + line + "]";
	}
}
