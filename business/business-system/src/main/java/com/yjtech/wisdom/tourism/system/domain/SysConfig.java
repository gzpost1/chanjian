package com.yjtech.wisdom.tourism.system.domain;

import com.yjtech.wisdom.tourism.common.annotation.Excel;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 参数配置表 sys_config
 *
 * @author liuhong
 */
@Data
public class SysConfig extends BaseEntity {
  private static final long serialVersionUID = 1L;

  /** 参数主键 */
  @Excel(name = "参数主键", cellType = Excel.ColumnType.NUMERIC)
  private Long configId;

  /** 参数名称 */
  @Excel(name = "参数名称")
  @NotBlank(message = "参数名称不能为空")
  @Size(min = 0, max = 100, message = "参数名称不能超过100个字符")
  private String configName;

  /** 参数键名 */
  @Excel(name = "参数键名")
  @NotBlank(message = "参数键名长度不能为空")
  @Size(min = 0, max = 100, message = "参数键名长度不能超过100个字符")
  private String configKey;

  /** 参数键值 */
  @Excel(name = "参数键值")
  @NotBlank(message = "参数键值不能为空")
  @Size(min = 0, max = 500, message = "参数键值长度不能超过500个字符")
  private String configValue;

  /** 系统内置（Y是 N否） */
  @Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
  private String configType;

  /** 备注 */
  private String remark;
}
