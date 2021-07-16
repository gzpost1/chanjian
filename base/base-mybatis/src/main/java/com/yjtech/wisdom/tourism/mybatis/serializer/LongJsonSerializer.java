package com.yjtech.wisdom.tourism.mybatis.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Objects;

/** Created by wuyongchong on 2019/8/20. */
public class LongJsonSerializer extends JsonSerializer<Long> {

  @Override
  public void serialize(
      Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    if (!Objects.isNull(value)) {
      jsonGenerator.writeString(value.toString());
    }
  }
}
