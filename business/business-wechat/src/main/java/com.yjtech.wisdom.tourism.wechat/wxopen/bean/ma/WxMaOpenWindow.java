package com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma;

import java.io.Serializable;
import lombok.Data;

/**
 * window对象
 *
 * @author yqx
 * @date 2018/9/13
 */
@Data
public class WxMaOpenWindow implements Serializable {
  private String navigationBarBackgroundColor;

  private String navigationBarTextStyle;

  private String navigationBarTitleText;

  private String navigationStyle;

  private String backgroundColor;

  private String backgroundTextStyle;

  private String backgroundColorTop;

  private String backgroundColorBottom;

  private Boolean enablePullDownRefresh;

  private Integer onReachBottomDistance;
}
