package com.yjtech.wisdom.tourism.wechat.wechat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import com.yjtech.wisdom.tourism.mybatis.serializer.LongJsonSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * tb_wechat_code_template
 *
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_wechat_code_template")
public class CodeTemplate extends BaseEntity {

  /**
   * 主键ID
   */
  @JsonSerialize(using = LongJsonSerializer.class)
  @TableId(value = "id", type = IdType.ID_WORKER)
  private Long id;
  /**
   * 模板ID
   */
  private Long templateId;

  /**
   * 模版名称
   */
  private String templateName;

  /**
   * 模版版本号
   */
  private String userVersion;

  /**
   * 模版描述
   */
  private String userDesc;

  /**
   * 状态(0:禁用,1:正常)
   */
  private Byte status;

}