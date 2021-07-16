package com.yjtech.wisdom.tourism.common.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JSONHelper {
    private static ObjectMapper objMapper = new ObjectMapper();
    private static JsonFactory jf;

    public JSONHelper() {
    }

    public static String toJSON(Object obj) throws Exception {
        StringWriter sw = new StringWriter();
        JsonGenerator jg = jf.createGenerator(sw);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objMapper.setDateFormat(df);
        objMapper.writeValue(jg, obj);
        return sw.toString();
    }

    public static <T> T fromJSON(String json, Class<T> cls) throws Exception {
        return objMapper.readValue(json, cls);
    }

    public static <T> T fromJSON(byte[] data, Class<T> cls) throws Exception {
        return objMapper.readValue(data, cls);
    }

    public static <T> List<T> fromJSONList(byte[] data, Class<T> element) throws Exception {
        JavaType type = TypeFactory.defaultInstance().constructCollectionType(List.class, element);
        return (List)objMapper.readValue(data, type);
    }

    public static <T> Set<T> fromJSONSet(byte[] data, Class<T> element) throws Exception {
        JavaType type = TypeFactory.defaultInstance().constructCollectionType(Set.class, element);
        return (Set)objMapper.readValue(data, type);
    }

    public static <T> List<T> convertToList(Object obj, Class<T> element) throws Exception {
        JavaType type = TypeFactory.defaultInstance().constructCollectionType(List.class, element);
        return (List)objMapper.convertValue(obj, type);
    }


    public static void hasWrapRoot(boolean hasOrNot) {
        objMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, hasOrNot);
    }

    public static Map<String, Object> toMap(String jsonStr) throws Exception {
        return (Map)objMapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {
        });
    }

    static {
        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        jf = new JsonFactory();
    }
}
