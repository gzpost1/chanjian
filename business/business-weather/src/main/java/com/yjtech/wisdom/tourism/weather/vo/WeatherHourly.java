package com.yjtech.wisdom.tourism.weather.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wuyongchong on 2019/11/18.
 */
@Data
public class WeatherHourly implements Serializable {

    private String code;

    private String updateTime;

    private String fxLink;

    private List<Hourly> hourly;

    @Data
    public static class Hourly {
        /**
         * 逐小时预报时间
         */
        private String fxTime;
        /**
         * 逐小时预报温度
         */
        private String temp;
        /**
         * 逐小时预报天气状况图标代码
         */
        private String icon;
        /**
         * 逐小时预报天气状况文字描述，包括阴晴雨雪等天气状态的描述
         */
        private String text;
        /**
         * 逐小时预报风向360角度
         */
        private String wind360;
        /**
         * 逐小时预报风向
         */
        private String windDir;
        /**
         * 逐小时预报风力等级
         */
        private String windScale;
        /**
         * 逐小时预报风速，公里/小时
         */
        private String windSpeed;
        /**
         * 逐小时预报相对湿度，百分比数值
         */
        private String humidity;
        /**
         *  	逐小时预报降水概率，百分比数值，可能为空
         */
        private String pop;
        /**
         *  	逐小时预报降水量，默认单位：毫米
         */
        private String precip;
        /**
         * 逐小时预报大气压强，默认单位：百帕
         */
        private String pressure;
        /**
         * 逐小时预报云量，百分比数值
         */
        private String cloud;
        /**
         * 逐小时预报露点温度
         */
        private String dew;
//        /**
//         * 本条预警的唯一标识，可判断本条预警是否已经存在，id有效期不超过72小时
//         */
//        private String snow;
//        /**
//         * 本条预警的唯一标识，可判断本条预警是否已经存在，id有效期不超过72小时
//         */
//        private String ice;

    }
}
