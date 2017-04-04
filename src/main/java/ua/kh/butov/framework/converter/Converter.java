package ua.kh.butov.framework.converter;

public interface Converter {
	<T> T convert(Class<T> entityClass, Object value);
}
