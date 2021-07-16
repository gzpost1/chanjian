package com.yjtech.wisdom.tourism.wechat.wxopen.bean.auth;

import java.util.List;
import java.util.Map;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

@Data
public class WxOpenMiniProgramInfo {
  private Map<String, List<String>> network;
  private List<Pair<String, String>> categories;
  private Integer visitStatus;
}
