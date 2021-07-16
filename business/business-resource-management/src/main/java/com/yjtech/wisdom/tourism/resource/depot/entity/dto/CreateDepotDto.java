package com.yjtech.wisdom.tourism.resource.depot.entity.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description
 * @Author 陈忠凌
 * @Date 2020-09-15 13:50
 */
@Getter
@Setter
public class CreateDepotDto {

    /**
     * 设备编号
     */
    @NotBlank(message = "设备编号必填")
    private String deviceId;

    /**
     * 停车场名称
     */
    @NotBlank(message = "停车场名称必填")
    private String name;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 维度
     */
    private String latitude;

    /**
     * 地址
     */
    private String address;

    /**
     * 停车位
     * */
    @NotNull(message = "停车位必填")
    private Integer spaceTotal;

    /**
     * 预警使用率
     */
    @NotNull(message = "预警使用率必填")
    private Float warnUsedRate;

    /**
     * 报警使用率
     */
    @NotNull(message = "报警使用率率必填")
    private Float alarmUsedRate;
}
