package com.yjtech.wisdom.tourism.mybatis.typehandler;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.yjtech.wisdom.tourism.mybatis.serializer.LongJsonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author shkstart
 * @create 2019-10-18 17:48
 */
@Slf4j
@MappedTypes({Object.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class JacksonTypeHandler extends BaseTypeHandler<Object> {

  private static ObjectMapper objectMapper;
  private Class<Object> type;

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

  public JacksonTypeHandler(Class<Object> type) {
    if (log.isTraceEnabled()) {
      log.trace("JacksonTypeHandler(" + type + ")");
    }
    if (null == type) {
      throw new MybatisPlusException("Type argument cannot be null");
    }
    this.type = type;
  }

  private Object parse(String json) {
    try {
      if (json == null || json.length() == 0) {
        return null;
      }
      return objectMapper.readValue(json, type);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String toJsonString(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
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

  @Override
  public void setNonNullParameter(
      PreparedStatement ps, int columnIndex, Object parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setString(columnIndex, toJsonString(parameter));
  }
}
