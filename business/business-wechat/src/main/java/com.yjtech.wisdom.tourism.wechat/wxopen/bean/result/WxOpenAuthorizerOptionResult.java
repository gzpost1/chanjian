package com.yjtech.wisdom.tourism.wechat.wxopen.bean.result;

import java.io.Serializable;
import lombok.Data;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
@Data
public class WxOpenAuthorizerOptionResult implements Serializable {
  private static final long serialVersionUID = 4477837353654658179L;

  String authorizerAppid;
  String optionName;
  String optionValue;
}
