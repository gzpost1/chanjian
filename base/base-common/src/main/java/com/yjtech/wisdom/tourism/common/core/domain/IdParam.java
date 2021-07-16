package com.yjtech.wisdom.tourism.common.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author liuhong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class IdParam implements Serializable {
  private static final long serialVersionUID=1L;

  @NotNull(message = "id can't be null")
  private Long id;
}
