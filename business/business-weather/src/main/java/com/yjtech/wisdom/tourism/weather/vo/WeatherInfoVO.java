package com.yjtech.wisdom.tourism.weather.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wuyongchong on 2019/11/18.
 */
@Data
public class WeatherInfoVO implements Serializable {

//    /**
//     * 今日数据
//     */
//    @JsonAlias("today")
//    private WeatherDaily.Daily today;
//
//    /**
//     * 实时空气质量
//     */
//    @JsonAlias("air")
//    private WeatherAirNow.Now air;

    /**
     * 24小时天气预报
     */
    private List<WeatherHourly.Hourly> hourly;

    /**
     * 一周天气预报
     */
    private List<WeatherDaily.Daily> weekly;

    /**
     * 气象预警
     */
    private List<WeatherWarning.Warning> warnings;
}

