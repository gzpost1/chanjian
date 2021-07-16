package com.yjtech.wisdom.tourism.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by wuyongchong on 2020/3/26.
 */
public class NullStringJsonSerializer extends JsonSerializer {

    public static final NullStringJsonSerializer INSTANCE = new NullStringJsonSerializer();

    @Override
    public Class handledType() {
        return Object.class;
    }

    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        if (value == null) {
            jsonGenerator.writeString("");
        } else {
            jsonGenerator.writeObject(value);
        }
    }
}
