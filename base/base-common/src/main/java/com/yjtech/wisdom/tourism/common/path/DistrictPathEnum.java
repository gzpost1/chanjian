package com.yjtech.wisdom.tourism.common.path;

/**
 * 区县大数据接口url
 *
 * @author renguangqian
 * @date 2021/7/22 15:39
 */
public enum  DistrictPathEnum {

    /**
     * 登录
     */
    LOGIN("/auth/login", "区县大数据登录"),

    /**
     * 数据总览
     */
    DATA_OVERVIEW("/api/v1/tx/totalnum", "区县大数据登录"),

    /**
     * 游客来源
     */
    TOURISTS_SOURCE("/api/v1/tx/statisticsvisit", "游客来源"),

    /**
     * 年客流趋势
     */
    YEAR_PASSENGER_FLOW("/api/v1/tx/visityeartrend", "年客流趋势"),

    /**
     * 月客流趋势
     */
    MONTH_PASSENGER_FLOW("/api/v1/tx/getdaytrend", "月客流趋势"),

    ;
    /**
     * 路径
     */
    private String path;

    /**
     * 描述
     */
    private String desc;

    DistrictPathEnum(String path, String desc) {
        this.path = path;
        this.desc = desc;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }}
