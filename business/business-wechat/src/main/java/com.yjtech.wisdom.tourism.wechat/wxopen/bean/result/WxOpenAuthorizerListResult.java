package com.yjtech.wisdom.tourism.wechat.wxopen.bean.result;

import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * @author robgao
 */
@Data
public class WxOpenAuthorizerListResult {
  private int totalCount;
  private List<Map<String,String>> list;
}
