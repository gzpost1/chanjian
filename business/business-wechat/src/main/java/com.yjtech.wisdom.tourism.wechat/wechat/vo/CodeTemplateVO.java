package com.yjtech.wisdom.tourism.wechat.wechat.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wuyongchong on 2019/11/8.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CodeTemplateVO implements Serializable {

  private Long templateId;
  private String templateName;
  private String userVersion;
  private String userDesc;
}
