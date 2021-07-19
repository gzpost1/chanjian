package com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yjtech.wisdom.tourism.mybatis.typehandler.JsonTypeHandler;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 一码游 投诉列表 BO
 *
 * @Date 2021/7/16 10:25
 * @Author horadirm
 */
@Data
public class OneTravelComplaintListBO implements Serializable {

    private static final long serialVersionUID = -936265732046763799L;

    /**
     * id
     */
    private Long id;

    /**
     * 投诉对象
     */
    private String complaintObject;

    /**
     * 联系人
     */
    private String complaintUser;

    /**
     * 联系电话
     */
    private String complaintPhone;

    /**
     * 处理状态（1待处理 2已处理 3已评价）
     */
    private String complaintStatus;

    /**
     * 处理状态描述
     */
    private String complaintStatusDesc;

    /**
     * 投诉原因
     */
    private String complaintContent;

    /**
     * 投诉图片
     */
    @TableField(typeHandler = JsonTypeHandler.class)
    private List<String> complaintImage;

    /**
     * 事发地点
     */
    private String happenLocation;

    /**
     * 投诉时间
     */
    private Date complaintTime;

    /**
     * 处理结果
     */
    private String operateContent;

    /**
     * 处理时间
     */
    private Date operateTime;

    /**
     * 处理人
     */
    private String operateUser;

    /**
     * 评价
     */
    private String evaluateContent;

    /**
     * 评价时间
     */
    private Date evaluateTime;

}
