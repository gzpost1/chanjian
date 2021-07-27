package com.yjtech.wisdom.tourism.resource.venue.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 文博场馆管理
 *
 * @author renguangqian
 * @date 2021/7/21 11:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@TableName("tb_wb_venue_manger")
public class VenueEntity implements Serializable {

    private static final long serialVersionUID = 3847077007129548072L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 场馆名称
     */
    private String venueName;

    /**
     * 场馆类型_通过字典管理配置
     */
    private String venueType;

    /**
     * 所在位置_可通过地图选点功能选择点位，记录地址及经纬度
     */
    private String position;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 开放日期-开始日期
     */
    @NotBlank
    private String openStartDate;

    /**
     * 开放日期-结束日期
     */
    @NotBlank
    private String openEndDate;

    /**
     * 开放日期-开始时间
     */
    private String openStartTime;

    /**
     * 开放日期-结束时间
     */
    private String openEndTime;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 封面图片Url
     */
    private String frontPicUrl;

    /**
     * 其他图片Url，多张用“,”逗号分割
     */
    private String otherPicUrl;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 修改人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 启停用状态
     */
    @TableField(fill = FieldFill.INSERT)
    private Byte status;

    /**
     * 是否删除, 0:否, 1:是
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    @JsonIgnore
    private Byte deleted;


    /**
     * 场馆类型数量
     */
    @TableField(exist = false)
    private Integer venueTypeNumber;
}
