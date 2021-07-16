package com.yjtech.wisdom.tourism.resource.wifi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 资源管理-WiFi设备
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WifiCreateDto {

    /**
     * name 设备名称
     */
    @NotBlank(message = "设备名称不能为空")
    @Length(max = 30, message = "设备名称最长30位")
    private String name;

    /**
     * device_id 设备编号
     */
    @NotBlank(message = "设备编号不能为空")
    @Length(max = 128, message = "设备编号最长128位")
    private String deviceId;

    /**
     * location 所在位置
     */
    @NotBlank(message = "所在位置不能为空")
    @Length(max = 100, message = "所在位置最长100位")
    private String address;

    /**
     * longitude 经度
     */
    @NotNull(message = "经度不能为空")
    private BigDecimal longitude;

    /**
     * latitude 纬度
     */
    @NotNull(message = "纬度不能为空")
    private BigDecimal latitude;

}