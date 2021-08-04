package com.yjtech.wisdom.tourism.resource.venue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 文博场馆
 *
 * @author renguangqian
 * @date 2021/7/21 20:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class VenueDto implements Serializable {

    private static final long serialVersionUID = 555206079268193546L;

    /**
     * id
     */
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
     * 场馆类型的值_通过字典管理配置
     */
    private String venueValue;

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
    private String openStartDate;

    /**
     * 开放日期-结束日期
     */
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
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * 启停用状态
     */
    private Byte status;
}
