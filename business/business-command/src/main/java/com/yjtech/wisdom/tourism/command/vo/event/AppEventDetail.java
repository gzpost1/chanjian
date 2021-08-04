package com.yjtech.wisdom.tourism.command.vo.event;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
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
public class AppEventDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
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
    private String eventTypeName;

    /**
     * 事件等级（数据字典）
     */
    private String eventLevel;

    /**
     * 事件等级名称
     */
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
    private List<String> imagePath;


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
    private String eventStatusName;

    /**
     * 处理单位
     */
    private String handleDepartment;


    /**
     * 处理日期
     */
    private Date handleDate;


    /**
     * 处理结果
     */
    private String handleResults;

    /**
     * 上报人id
     */
    private Long createUser;

    /**
     * 上报人名称
     */
    private String createUserName;

    /**
     * 预案信息
     */
    private AppEmergencyPlanVO plan;

    /**
     * 当前登录人是否是指定处理人员
     */
    private Boolean appointPersonnel;

    /**
     * 指定处理人
     */
    private List<String> appointHandlePersonnel;

    /**
     * 实际处理人员
     */
    private Long actualHandlePersonnel;


    /**
     * 实际处理人员名称
     */
    private String actualHandlePersonnelName;
}
