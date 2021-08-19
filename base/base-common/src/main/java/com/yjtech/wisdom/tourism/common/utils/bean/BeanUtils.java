package com.yjtech.wisdom.tourism.common.utils.bean;

import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Bean 工具类
 *
 * @author liuhong
 */
@Slf4j
public class BeanUtils extends org.springframework.beans.BeanUtils {
    /**
     * Bean方法名中属性名开始的下标
     */
    private static final int BEAN_METHOD_PROP_INDEX = 3;

    /**
     * 匹配getter方法的正则表达式
     */
    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");

    /**
     * 匹配setter方法的正则表达式
     */
    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");

    /**
     * Bean属性复制工具方法。
     *
     * @param dest 目标对象
     * @param src  源对象
     */
    public static void copyBeanProp(Object dest, Object src) {
        try {
            copyProperties(src, dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取对象的setter方法。
     *
     * @param obj 对象
     * @return 对象的setter方法列表
     */
    public static List<Method> getSetterMethods(Object obj) {
        // setter方法列表
        List<Method> setterMethods = new ArrayList<Method>();

        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();

        // 查找setter方法

        for (Method method : methods) {
            Matcher m = SET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 1)) {
                setterMethods.add(method);
            }
        }
        // 返回setter方法列表
        return setterMethods;
    }

    /**
     * 获取对象的getter方法。
     *
     * @param obj 对象
     * @return 对象的getter方法列表
     */
    public static List<Method> getGetterMethods(Object obj) {
        // getter方法列表
        List<Method> getterMethods = new ArrayList<Method>();
        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();
        // 查找getter方法
        for (Method method : methods) {
            Matcher m = GET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 0)) {
                getterMethods.add(method);
            }
        }
        // 返回getter方法列表
        return getterMethods;
    }

    /**
     * 检查Bean方法名中的属性名是否相等。<br>
     * 如getName()和setName()属性名一样，getName()和setAge()属性名不一样。
     *
     * @param m1 方法名1
     * @param m2 方法名2
     * @return 属性名一样返回true，否则返回false
     */
    public static boolean isMethodPropEquals(String m1, String m2) {
        return m1.substring(BEAN_METHOD_PROP_INDEX).equals(m2.substring(BEAN_METHOD_PROP_INDEX));
    }

    /**
     * Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean
     *
     * @param map
     * @param obj
     */
    public static void transMap2Bean2(Map<String, Object> map, Object obj)
            throws InvocationTargetException, IllegalAccessException {
        if (map == null || obj == null) {
            return;
        }
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);
    }

    /**
     * Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
     *
     * @param map
     * @param obj
     */
    public static void transMap2Bean(Map<String, Object> map, Object obj)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (map.containsKey(key)) {
                Object value = map.get(key);
                // 得到property对应的setter方法
                Method setter = property.getWriteMethod();
                setter.invoke(obj, value);
            }
        }
    }

    /**
     * Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
     *
     * @param obj
     */
    public static Map<String, Object> transBean2Map(Object obj)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();

            // 过滤class属性
            if (!key.equals("class")) {
                // 得到property对应的getter方法
                Method getter = property.getReadMethod();
                Object value = getter.invoke(obj);
                if (Objects.isNull(value)) {
                    continue;
                }
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * 实体转换Map
     *
     * @param tBean
     * @param <T>
     * @return
     */
    public static <T, V> Map<String, V> beanToMap(T tBean) {
        Map<String, V> map = new HashMap<>(16);

        if (Objects.isNull(tBean)) {
            return map;
        }
        try {
            //属性列表
            List<Field> fieldList = new ArrayList<>();

            Class<?> tBeanClass = tBean.getClass();
            while (Objects.nonNull(tBeanClass)) {
                fieldList.addAll(Arrays.asList(tBeanClass.getDeclaredFields()));
                tBeanClass = tBeanClass.getSuperclass();
            }

            for (Field field : fieldList) {
                field.setAccessible(true);
                //排除static、final属性
                if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                //获取属性值
                Object value = field.get(tBean);
                //无效属性值不作映射
                if (Objects.isNull(value)) {
                    continue;
                }
                map.put(field.getName(), (V) value);
            }
        } catch (Exception e) {
            log.info("====================> {} <====================\n", "实体转换Map异常");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * copy类 ,可防止LocalDateTime类型复制出错
     *
     * @param source
     * @param destinationClass
     * @param <T>
     * @return
     */
    public static <T> T copyBean(Object source, Class<T> destinationClass) {
        T t = null;
        try {
            t = destinationClass.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source, t);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

}
