package com.yjtech.wisdom.tourism.infrastructure.core.domain.entity;

import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 字典类型表 sys_dict_type
 *
 * @author liuhong
 */
@Data
public class SysDictType extends BaseEntity {
  private static final long serialVersionUID = 1L;

  /** 字典主键 */
  private Long dictId;

  /** 字典名称 */
  @NotBlank(message = "字典名称不能为空")
  @Size(min = 0, max = 100, message = "字典类型名称长度不能超过100个字符")
  private String dictName;

  /** 字典类型 */
  @NotBlank(message = "字典类型不能为空")
  @Size(min = 0, max = 100, message = "字典类型类型长度不能超过100个字符")
  private String dictType;

  /** 状态（0正常 1停用） */
  private String status;

  /**
   * 备注
   */
  private String remark;
}
