package com.yjtech.wisdom.tourism.wechat.wxopen.bean.message;

import com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma.WxOpenMaSubmitAudit;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 微信小程序代码包提交审核(仅供第三方开发者代小程序调用)
 *
 * @author yqx
 * @date 2018/9/13
 */
@Data
public class WxOpenMaSubmitAuditMessage implements Serializable {

  @SerializedName("item_list")
  private List<WxOpenMaSubmitAudit> itemList;
}
