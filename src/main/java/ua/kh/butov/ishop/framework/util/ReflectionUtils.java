package ua.kh.butov.ishop.framework.util;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import ua.kh.butov.ishop.framework.FrameworkSystemException;
import ua.kh.butov.ishop.framework.annotation.jdbc.Column;
import ua.kh.butov.ishop.framework.annotation.jdbc.Transient;

public class ReflectionUtils {

	public static List<Field> getAccessibleEntityFields(Class<?> entityClass) {
		List<Field> res = new ArrayList<>();
		while (entityClass != Object.class) {
			for (Field field : entityClass.getDeclaredFields()) {
				if (shouldFieldBeIncluded(field)) {
					field.setAccessible(true);
					res.add(field);
				}
			}
			entityClass = entityClass.getSuperclass();
		}
		return res;
	}

	private static boolean shouldFieldBeIncluded(Field field) {
		int modifiers = field.getModifiers();
		return (modifiers & (Modifier.STATIC | Modifier.FINAL)) == 0 && field.getAnnotation(Transient.class) == null;
	}
	
	public static String getColumnNameForField(Field field) {
		Column columnAnnotation = field.getAnnotation(Column.class);
		if (columnAnnotation != null) {
			return columnAnnotation.value();
		} else {
			return field.getName();
		}
	}
	
	public static Field findField(Class<?> fieldClass, List<Field> fields, String fieldName) {
		for(Field field : fields) {
			if(field.getName().equals(fieldName)) {
				return field;
			}
		}
		throw new FrameworkSystemException("Field "+fieldName+" not found for class: "+fieldClass);
	}
}
