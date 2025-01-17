package com.yjtech.wisdom.tourism.common.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Retention(RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface MapIgnore {
}