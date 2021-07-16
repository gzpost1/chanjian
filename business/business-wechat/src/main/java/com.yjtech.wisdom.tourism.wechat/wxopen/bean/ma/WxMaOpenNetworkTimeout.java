package com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma;

import java.io.Serializable;
import lombok.Data;

/**
 * @author yqx
 * @date 2018/9/13
 */
@Data
public class WxMaOpenNetworkTimeout implements Serializable {

  private Integer request;

  private Integer connectSocket;

  private Integer uploadFile;

  private Integer downloadFile;
}
