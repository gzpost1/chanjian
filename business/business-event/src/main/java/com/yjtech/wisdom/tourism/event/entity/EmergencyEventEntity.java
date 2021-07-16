package com.yjtech.wisdom.tourism.event.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import com.yjtech.wisdom.tourism.mybatis.typehandler.JsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 应急事件
 * </p>
 *
 * @author wuyongchong
 * @since 2021-02-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_event",autoResultMap = true)
public class EmergencyEventEntity extends BaseEntity {

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
     * 事件来源（数据字典）
     */
    private String eventSource;

    /**
     * 事件来源名称
     */
    @TableField(exist = false)
    private String eventSourceName;

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
    private BigDecimal latitude;


    /**
     * 经度
     */
    private BigDecimal longitude;


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
     * 视频流地址
     */
    private String videoPath;

    /**
     * 视频状态 0：直播中 1: 直播中断 2:直播结束
     */
    private String videoStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime videoHeartTime;

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

}
