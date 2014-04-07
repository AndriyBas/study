package dao.annotation.utils.converter;

import java.util.UUID;

import dao.annotation.utils.ValueConverter;

public class intConverter implements ValueConverter {

	@Override
	public <T> String toString(T value) {
		if(value == null) return "null";
		return Integer.toString((Integer)value);
	}

	@Override
	public <T> T toValue(String str) {
		if(str.equals("null")) return null;
		return (T)new Integer(Integer.parseInt(str));
	}
}
