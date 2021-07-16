package com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma;

import java.io.Serializable;
import lombok.Data;

/**
 * 微信开放平台小程序成员对象
 *
 * @author yqx
 * @date 2018/9/12
 */
@Data
public class WxOpenMaMember implements Serializable {
  /**
   * 人员对应的唯一字符串
   */
  private String userstr;
}
