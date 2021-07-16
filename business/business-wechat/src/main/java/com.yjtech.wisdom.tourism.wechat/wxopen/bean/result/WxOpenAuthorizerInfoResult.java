package com.yjtech.wisdom.tourism.wechat.wxopen.bean.result;

import com.yjtech.wisdom.tourism.wechat.wxopen.bean.auth.WxOpenAuthorizationInfo;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.auth.WxOpenAuthorizerInfo;
import java.io.Serializable;
import lombok.Data;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
@Data
public class WxOpenAuthorizerInfoResult implements Serializable {
  private static final long serialVersionUID = 3166298050833019785L;

  private WxOpenAuthorizationInfo authorizationInfo;
  private WxOpenAuthorizerInfo authorizerInfo;

  public boolean isMiniProgram() {
    return authorizerInfo != null && authorizerInfo.getMiniProgramInfo() != null;
  }
}
