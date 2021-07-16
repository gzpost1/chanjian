package com.yjtech.wisdom.tourism.wechat.wxopen.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Created by wuyongchong on 2019/9/20.
 */
@Data
public class CodeCommitParam implements Serializable {
  @NotNull(message = "企业ID不能为空")
  private Long companyId;
  @NotBlank(message = "小程序appId不能为空")
  private String appId;
  @NotNull(message = "模板Id不能为空")
  private Long templateId;
  @NotBlank(message = "版本不能为空")
  private String userVersion;
  @NotBlank(message = "描述不能为空")
  private String userDesc;
}
