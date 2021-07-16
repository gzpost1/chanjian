package com.yjtech.wisdom.tourism.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by wuyongchong on 2020/3/26.
 */
public class NullArrayJsonSerializer extends JsonSerializer {

    public static final NullArrayJsonSerializer INSTANCE = new NullArrayJsonSerializer();

    @Override
    public Class handledType() {
        return Object.class;
    }

    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        if (value == null) {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeEndArray();
        } else {
            jsonGenerator.writeObject(value);
        }
    }
}
