package com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author yqx
 * @date 2018/9/13
 */
@Data
public class WxMaOpenTab implements Serializable {
  private String pagePath;
  private String text;
  private String iconPath;
  private String selectedIconPath;
}
