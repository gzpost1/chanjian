package com.yjtech.wisdom.tourism.weather.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wuyongchong on 2019/11/18.
 */
@Data
public class WeatherWarning implements Serializable {

    private String code;

    private String updateTime;

    private String fxLink;

    private List<Warning> warning;

    @Data
    public static class Warning {
        /**
         * 本条预警的唯一标识，可判断本条预警是否已经存在，id有效期不超过72小时
         */
        private String id;
        /**
         * 预警发布单位，可能为空
         */
        private String sender;
        /**
         * 预警发布时间
         */
        private String pubTime;
        /**
         * 预警信息标题
         */
        private String title;
        /**
         * 预警开始时间，可能为空。
         */
        private String startTime;
        /**
         * 预警结束时间，可能为空。
         */
        private String endTime;
        /**
         * 预警状态，可能为空
         * active 预警中或首次预警
         * update 预警信息更新
         * cancel 取消预警
         */
        private String status;
        /**
         * 预警等级
         */
        private String level;
        /**
         * 预警类型
         */
        private String type;
        /**
         * 预警等级名称
         */
        private String typeName;
        /**
         * 预警详细文字描述
         */
        private String text;

    }
}
