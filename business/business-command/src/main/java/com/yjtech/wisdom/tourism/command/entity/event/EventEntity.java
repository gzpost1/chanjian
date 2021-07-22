package com.yjtech.wisdom.tourism.command.entity.event;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import com.yjtech.wisdom.tourism.mybatis.typehandler.JsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 应急事件
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_event")
public class EventEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("id")
    private Long id;


    /**
     * 事件名称
     */
    private String name;


    /**
     * 事件类型（数据字典）
     */
    private String eventType;

    /**
     * 事件类型名称
     */
    @TableField(exist = false)
    private String eventTypeName;

    /**
     * 事件等级（数据字典）
     */
    private String eventLevel;

    /**
     * 事件等级名称
     */
    @TableField(exist = false)
    private String eventLevelName;

    /**
     * 事件日期
     */
    private LocalDate eventDate;


    /**
     * 地址
     */
    private String address;


    /**
     * 纬度
     */
    private String latitude;


    /**
     * 经度
     */
    private String longitude;


    /**
     * 事件过程
     */
    private String description;


    /**
     * 图片路径
     */
    @TableField(typeHandler = JsonTypeHandler.class)
    private List<String> imagePath;


    /**
     * 指定处理人员
     */
    @TableField(typeHandler = JsonTypeHandler.class)
    private List<String> appointHandlePersonnel;

    /**
     * 指派状态
     */
    private String appointStatus;

    /**
     * 指派状态名
     */
    @TableField(exist = false)
    private String appointStatusName;

    /**
     * 实际处理人员
     */
    private Long actualHandlePersonnel;

    /**
     * 预案id
     */
    private Long planId;

    /**
     * 事件状态(数据字典)
     */
    private String eventStatus;

    /**
     * 事件状态名称
     */
    @TableField(exist = false)
    private String eventStatusName;

    /**
     * 处理单位
     */
    private String handleDepartment;


    /**
     * 处理人员
     */
    private String handlePersonnel;


    /**
     * 处理日期
     */
    private LocalDate handleDate;


    /**
     * 处理结果
     */
    private String handleResults;

    /**
     * 上报人名称
     */
    @TableField(exist = false)
    private String createUserName;

    /**
     * 图标地址
     */
    @TableField(exist = false)
    private String iconUrl;

    /**
     * 状态(0:禁用,1:正常)
     */
    private Byte status;
}
