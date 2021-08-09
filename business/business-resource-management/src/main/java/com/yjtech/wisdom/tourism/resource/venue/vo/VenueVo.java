package com.yjtech.wisdom.tourism.resource.venue.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 场馆信息
 *
 * @author renguangqian
 * @date 2021/7/21 19:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class VenueVo extends PageQuery implements Serializable {

    private static final long serialVersionUID = -40527196838634864L;

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
    private List<String> phone;

    /**
     * 封面图片Url
     */
    private String frontPicUrl;

    /**
     * 其他图片Url
     */
    private List<String> otherPicUrl;

    /**
     * 简介
     */
    private String introduction;

}
