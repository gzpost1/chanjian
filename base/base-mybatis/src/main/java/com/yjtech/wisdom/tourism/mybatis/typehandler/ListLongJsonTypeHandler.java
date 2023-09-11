package com.yjtech.wisdom.tourism.mybatis.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.yjtech.wisdom.tourism.common.utils.JsonUtil;
import com.yjtech.wisdom.tourism.mybatis.serializer.LongJsonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wuyongchong
 * @date: 2020/4/30 13:42
 */
@Slf4j
public class ListLongJsonTypeHandler extends BaseTypeHandler<List<Long>> {

  private List<Long> parse(String json) {
    if (json == null || json.length() == 0) {
      return null;
    }
    return JsonUtil.readValue(json, new TypeReference<List<Long>>() {
    });
  }

  private String toJsonString(Object object) {
    return JsonUtil.writeValueAsString(object);
  }

  @Override
  public void setNonNullParameter(
      PreparedStatement ps, int columnIndex, List<Long> parameter, JdbcType jdbcType)
      throws SQLException {
      ps.setString(columnIndex, toJsonString(parameter));
  }

  @Override
  public List<Long> getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return parse(rs.getString(columnName));
  }

  @Override
  public List<Long> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return parse(rs.getString(columnIndex));
  }

  @Override
  public List<Long> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return parse(cs.getString(columnIndex));
  }
}
