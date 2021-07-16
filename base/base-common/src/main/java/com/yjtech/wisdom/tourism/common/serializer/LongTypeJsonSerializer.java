package com.yjtech.wisdom.tourism.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class LongTypeJsonSerializer extends JsonSerializer<Long> {

  public static final LongTypeJsonSerializer instance = new LongTypeJsonSerializer();

  private static final List<String> noneList;

  static {
    noneList = Lists.newArrayList();
    noneList.add("total");
    noneList.add("size");
    noneList.add("current");
    noneList.add("pages");
    noneList.add("roleId");
  }

  public LongTypeJsonSerializer() {
  }

  @Override
  public void serialize(Long val, JsonGenerator jsonGenerator,
                        SerializerProvider serializerProvider) throws IOException {
    if (!Objects.isNull(val)) {
      String currentName = null;
      if (null != jsonGenerator.getOutputContext()) {
        currentName = jsonGenerator.getOutputContext().getCurrentName();
      }
      if (StringUtils.isNotBlank(currentName) && noneList.contains(currentName)) {
        jsonGenerator.writeNumber(val);
      } else {
        jsonGenerator.writeString(val.toString());
      }
    }
  }

}