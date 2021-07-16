package com.yjtech.wisdom.tourism.wechat.wechat.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wuyongchong on 2019/12/11.
 */
@Setter
@Getter
public class ExtJsonVO implements Serializable {
  private Boolean extEnable = true;
  private String extAppid;
  private Boolean directCommit = false;
  private Map ext;
}
