package com.yjtech.wisdom.tourism.common.core.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by wuyongchong on 2019/10/21.
 */
@Data
public class DeleteParam implements Serializable {
  @NotNull(message = "ID参数不能为空")
  private Long id;
}
