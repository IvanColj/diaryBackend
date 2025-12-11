package org.spring.diaryBackend.logic;

import java.lang.reflect.Field;

public class BeanUtils {
    public static <T> void copyNonNullProperties(T source, T target) {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(source);
                if (value != null) {
                    field.set(target, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Could not copy field " + field.getName(), e);
            }
        }
    }
}
