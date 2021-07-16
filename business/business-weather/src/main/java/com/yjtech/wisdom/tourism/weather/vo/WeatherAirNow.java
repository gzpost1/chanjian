package com.yjtech.wisdom.tourism.weather.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by wuyongchong on 2019/11/19.
 */
@Data
public class WeatherAirNow implements Serializable {
    private String code;

    private String updateTime;

    private String fxLink;

    private Now now;

    @Data
    public static class Now {
        /**
         * 实时空气质量数据发布时间
         */
        private String pubTime;

        /**
         * 实时空气质量指数
         */
        private String aqi;
        /**
         * 实时空气质量指数等级
         */
        private String level;
        /**
         * 实时空气质量指数级别
         */
        private String category;
        /**
         * 实时空气质量的主要污染物，空气质量为优时，返回值为NA
         */
        private String primary;
        /**
         * 实时 pm10
         */
        private String pm10;
        /**
         * 实时 pm2.5
         */
        private String pm2p5;
        /**
         * 实时 二氧化氮
         */
        private String no2;
        /**
         * 实时 二氧化硫
         */
        private String so2;
        /**
         * 实时 一氧化碳
         */
        private String co;
        /**
         * 实时 臭氧
         */
        private String o3;

    }
}
