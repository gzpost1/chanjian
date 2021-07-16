package com.yjtech.wisdom.tourism.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** Created by wuyongchong on 2019/8/19. */
@Data
public abstract class BaseEntity implements Serializable {
  /** 创建时间 */
  @TableField(fill = FieldFill.INSERT)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;

  /** 修改时间 */
  @TableField(fill = FieldFill.UPDATE)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updateTime;

  /** 创建人ID */
  @JsonIgnore
  @TableField(fill = FieldFill.INSERT)
  private Long createUser;

  /** 修改人ID */
  @JsonIgnore
  @TableField(fill = FieldFill.UPDATE)
  private Long updateUser;

  /** 是否删除, 0:否, 1:是 */
  @TableField(fill = FieldFill.INSERT)
  @TableLogic
  @JsonIgnore
  private Byte deleted;

  /** 搜索值 */
  @TableField(exist = false)
  @JsonIgnore
  private String searchValue;

  /** 开始时间 */
  @JsonIgnore
  @TableField(exist = false)
  private String beginTime;

  /** 结束时间 */
  @JsonIgnore
  @TableField(exist = false)
  private String endTime;

  /** 请求参数 */
  @TableField(exist = false)
  @JsonIgnore
  private Map<String, Object> params;

  public Map<String, Object> getParams() {
    if (params == null) {
      params = new HashMap<>();
    }
    return params;
  }
}
