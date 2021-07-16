package com.yjtech.wisdom.tourism.systemconfig.area.query;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by wuyongchong on 2019/10/21.
 */
@Data
public class DeleteParam implements Serializable {
  @NotNull(message = "code参数不能为空")
  private String code;
}
