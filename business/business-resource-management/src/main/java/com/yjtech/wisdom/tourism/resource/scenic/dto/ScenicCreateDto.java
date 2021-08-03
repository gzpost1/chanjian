package com.yjtech.wisdom.tourism.resource.scenic.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class ScenicCreateDto {

    /**景区名称*/
    @NotBlank(message = "景区名称不能为空")
    private String name;

    /**景区等级*/
    private String level;

    /**地址*/
    private String address;

    /**纬度*/
    private String latitude;

    /**经度*/
    private String longitude;

    /**地图缩放比例*/
    private Integer mapZoomRate;

    /** 开放日期-开始日期*/
    private String openStartDate;

    /**开放日期-结束日期*/
    private String openEndDate;

    /**开放日期-开始时间*/
    private String openStartTime;

    /**开放日期-结束时间*/
    private String openEndTime;

    /**景区承载量*/
    private Integer bearCapacity;

    /**舒适度预警比例*/
    private BigDecimal comfortWarnRate;

    /**联系电话*/
    private String phone;

    /**应急联系人*/
    private String emergencyContact;

    /**应急联系人电话*/
    private String emergencyContactPhone;

    /**封面图片Url*/
    private String frontPicUrl;

    /**其他图片Url，多张用“,”逗号分割*/
    private String otherPicUrl;

    /**简介*/
    private String introduction;
}
