package com.yjtech.wisdom.tourism.wechat.wxopen.dto;

import com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma.WxOpenMaSubmitAudit;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Created by wuyongchong on 2019/9/20.
 */
@Data
public class SubmitAuditParam implements Serializable {
  @NotBlank(message = "appId不能为空")
  private String appId;

  private List<WxOpenMaSubmitAudit> itemList;

}
