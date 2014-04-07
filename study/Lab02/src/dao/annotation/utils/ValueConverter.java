package dao.annotation.utils;

public interface ValueConverter {
	public <T> String toString(T value);
	public <T> T toValue(String str);
}
