package com.yjtech.wisdom.tourism.mybatis.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.yjtech.wisdom.tourism.mybatis.serializer.LongJsonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: wuyongchong
 * @date: 2020/4/30 13:42
 */
@Slf4j
public class ListObjectJsonTypeHandler extends BaseTypeHandler<Object> {

  private static ObjectMapper objectMapper;

  private Class<Object> clazz;

  static {
    objectMapper = new ObjectMapper().findAndRegisterModules();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    SimpleModule module = new SimpleModule();
    module.addSerializer(Long.TYPE, new LongJsonSerializer());
    module.addSerializer(Long.class, new LongJsonSerializer());
    objectMapper.registerModule(module);
  }

  public ListObjectJsonTypeHandler(Class<Object> clazz) {
    if (clazz == null) {
      throw new IllegalArgumentException("Type argument cannot be null");
    }

    this.clazz = clazz;
  }

  private Object parse(String json) {
    try {
      if (json == null || json.length() == 0) {
        return null;
      }
      JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
      return objectMapper.readValue(json, javaType);
    } catch (IOException e) {
      log.error("deserialization failed {}", json, e);
      throw new RuntimeException(e);
    }
  }

  private String toJsonString(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      log.error("serialization failed {}", object, e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public void setNonNullParameter(
      PreparedStatement ps, int columnIndex, Object parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setString(columnIndex, toJsonString(parameter));
  }

  @Override
  public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return parse(rs.getString(columnName));
  }

  @Override
  public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return parse(rs.getString(columnIndex));
  }

  @Override
  public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return parse(cs.getString(columnIndex));
  }
}
