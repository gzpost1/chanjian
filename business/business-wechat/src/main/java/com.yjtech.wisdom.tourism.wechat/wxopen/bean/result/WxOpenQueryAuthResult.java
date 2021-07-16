package com.yjtech.wisdom.tourism.wechat.wxopen.bean.result;

import com.yjtech.wisdom.tourism.wechat.wxopen.bean.auth.WxOpenAuthorizationInfo;
import java.io.Serializable;
import lombok.Data;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
@Data
public class WxOpenQueryAuthResult implements Serializable {
  private static final long serialVersionUID = 2394736235020206855L;

  private WxOpenAuthorizationInfo authorizationInfo;
}
