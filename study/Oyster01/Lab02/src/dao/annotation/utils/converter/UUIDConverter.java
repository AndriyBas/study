package dao.annotation.utils.converter;

import java.util.UUID;

import dao.annotation.utils.ValueConverter;

public class UUIDConverter implements ValueConverter {

	@Override
	public <T> String toString(T value) {
		if(value == null) return null;
		return  "\""+((Object)value).toString()+"\"";
	}

	@Override
	public <T> T toValue(String str) {
		if(str==null) return null;
		if(str.equals("null")) return null;
//		str = str.substring(1, str.length()-1);
		return (T)UUID.fromString(str);
	}
}
