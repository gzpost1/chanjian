package com.yjtech.wisdom.tourism.weather.web;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author liuhong
 */
@Data
public class WeatherQuery implements Serializable {
    /**
     * 经度
     */
    @NotBlank(message = "经度不能为空")
    private String longitude;
    /**
     * 纬度
     */
    @NotBlank(message = "纬度不能为空")
    private String latitude;
}
