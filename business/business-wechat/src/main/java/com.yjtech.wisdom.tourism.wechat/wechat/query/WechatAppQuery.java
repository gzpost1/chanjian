package com.yjtech.wisdom.tourism.wechat.wechat.query;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;

/**
 * Created by wuyongchong on 2019/9/30.
 */
@Data
public class WechatAppQuery extends PageQuery {

  private Long companyId;
  private String companyName;
  private String appId;
  private String appName;
  private Byte status;

}
