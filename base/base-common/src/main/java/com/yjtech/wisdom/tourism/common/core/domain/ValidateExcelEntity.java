package com.yjtech.wisdom.tourism.common.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Tree基类
 *
 * @author liuhong
 */
@Data
@Builder
public class ValidateExcelEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  private boolean isPass;
  @JsonIgnore
  private List excelList;
  private List errorExcelList;
  private List<String> errorMsg;

  public boolean isPass() {
    return Objects.isNull(errorExcelList) || errorExcelList.size() == 0;
  }
}
