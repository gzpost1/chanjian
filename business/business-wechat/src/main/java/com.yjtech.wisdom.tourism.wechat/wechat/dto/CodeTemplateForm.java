package com.yjtech.wisdom.tourism.wechat.wechat.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by wuyongchong on 2020/1/15.
 */
@Data
public class CodeTemplateForm implements Serializable {

  private Long id;

  /**
   * 模板ID
   */
  @NotNull(message = "模板ID不能为空")
  private Long templateId;

  /**
   * 模版名称
   */
  @NotBlank(message = "模版名称不能为空")
  private String templateName;

  /**
   * 模版版本号
   */
  @NotBlank(message = "模版版本号不能为空")
  private String userVersion;

  /**
   * 模版描述
   */
  @NotBlank(message = "模版描述不能为空")
  private String userDesc;

}
