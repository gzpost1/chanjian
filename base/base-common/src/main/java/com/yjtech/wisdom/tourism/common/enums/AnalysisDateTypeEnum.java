package com.yjtech.wisdom.tourism.common.enums;

import lombok.Getter;

import java.time.temporal.ChronoUnit;

/**
 * 趋势时间类型
 *
 * @Author horadirm
 * @Date 2021/5/27 11:04
 */
public enum AnalysisDateTypeEnum {


    /**
     * 年
     */
    ANALYSIS_DATE_TYPE_YEAR((byte)1, "年", "yyyy", "%Y", ChronoUnit.YEARS,"yyyy"),

    /**
     * 年月
     */
    ANALYSIS_DATE_TYPE_YEAR_MONTH((byte)2, "年月", "yyyy-MM", "%Y-%m", ChronoUnit.MONTHS,"MM"),

    /**
     * 年月日
     */
    ANALYSIS_DATE_TYPE_YEAR_MONTH_DAY((byte)3, "年月日", "yyyy-MM-dd", "%Y-%m-%d", ChronoUnit.DAYS,"dd"),

    /**
     * 年月日时
     */
    ANALYSIS_DATE_TYPE_YEAR_MONTH_DAY_HOUR((byte)4, "年月日时", "yyyy-MM-dd HH", "%Y-%m-%d %H", ChronoUnit.HOURS,"HH")

    ;

    @Getter
    private Byte value;
    @Getter
    private String describe;
    @Getter
    private String javaDateFormat;
    @Getter
    private String sqlDateFormat;
    @Getter
    private ChronoUnit chronoUnit;
    @Getter
    private  String coordinateJavaDateFormat;

    AnalysisDateTypeEnum(Byte value, String describe, String javaDateFormat, String sqlDateFormat, ChronoUnit chronoUnit, String coordinateJavaDateFormat) {
        this.value = value;
        this.describe = describe;
        this.javaDateFormat = javaDateFormat;
        this.sqlDateFormat = sqlDateFormat;
        this.chronoUnit = chronoUnit;
        this.coordinateJavaDateFormat = coordinateJavaDateFormat;
    }

    /**
     * 根据值获取枚举
     *
     * @param value
     * @return
     */
    public static AnalysisDateTypeEnum getItemByValue(Byte value) {
        for (AnalysisDateTypeEnum item : AnalysisDateTypeEnum.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

}
