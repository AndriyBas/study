package dao.annotation.utils.converter;

import dao.annotation.utils.ValueConverter;

public class StringConverter implements ValueConverter {

	@Override
	public <T> String toString(T value) {
		if(value == null) return null;
		return "\""+((Object)value).toString()+"\"";
	}

	@Override
	public <T> T toValue(String str) {
		if (str==null) return null;
		if(str.equals("null")) return null;
		if(str.equals("\"\"")) return (T) "";
		if(str.equals("")) return (T) "";
		
//		str = str.substring(1, str.length()-1);
		return (T)str;
	}
}
