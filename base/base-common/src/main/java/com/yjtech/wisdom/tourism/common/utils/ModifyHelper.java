package com.yjtech.wisdom.tourism.common.utils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ModifyHelper {
    private static final Map<Class<?>, List<Field>> fieldFilterCache = new ConcurrentHashMap<>();

    public ModifyHelper() {
    }

    public static <T, U> void copyValueForModify(T src, U target) {
        Class<?> srcClz = src.getClass();
        Class<?> targetClz = target.getClass();
        List<Field> targetFields = getFields(targetClz);
        targetFields.forEach((targetField) -> {
            try {
                Optional.of(srcClz.getDeclaredField(targetField.getName())).ifPresent((srcField) -> {
                    try {
                        srcField.setAccessible(true);
                        targetField.setAccessible(true);
                        targetField.set(target, srcField.get(src));
                    } catch (IllegalAccessException var5) {
                    }

                });
            } catch (NoSuchFieldException var5) {
            }

        });
    }

    private static List<Field> getFields(Class<?> clz) {
        List<Field> fieldHolders = fieldFilterCache.get(clz);
        if (Objects.isNull(fieldHolders) || fieldHolders.isEmpty()) {
            fieldHolders = Arrays.stream(clz.getDeclaredFields())
                    .filter((field) -> Optional.ofNullable(field.getAnnotation(Modifiable.class))
                            .isPresent()).collect(Collectors.toList());
            fieldFilterCache.putIfAbsent(clz, fieldHolders);
        }

        return fieldHolders;
    }
}