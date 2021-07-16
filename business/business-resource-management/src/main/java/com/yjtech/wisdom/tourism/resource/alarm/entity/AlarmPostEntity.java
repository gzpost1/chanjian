package com.yjtech.wisdom.tourism.resource.alarm.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjtech.wisdom.tourism.mybatis.entity.MyBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 报警柱
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_alarm_post")
public class AlarmPostEntity extends MyBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("id")
    private Long id;


    /**
     * 设备编号
     */
    @TableId("code")
    private String code;

    /**
     * 设备名称
     */
    private String name;


    /**
     * 位置
     */
    private String address;


    /**
     * 经度
     */
    private String latitude;


    /**
     * 维度
     */
    private String longitude;


    /**
     * 设备状态(0:停用,1:正常)
     */
    private Byte equipStatus;


    /**
     * 状态(0:禁用,1:正常)
     */
    private Byte status;


    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updateTime;
}
