package com.yjtech.wisdom.tourism.common.enums;

/**
 * 导入信息类型
 *
 * @Author horadirm
 * @Date 2020/11/3 10:04
 */
public enum ImportInfoTypeEnum {

    /**
     * 监控
     */
    TYPE_SUPERVISION(1, "监控导入", "video"),

    /**
     * 项目
     */
    PROJECT(2, "招商平台项目信息登记表（模板）（试行）.xls", "project");

    private Integer value;
    private String describe;
    private String label;

    ImportInfoTypeEnum(Integer value, String describe, String label) {
        this.value = value;
        this.describe = describe;
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescribe() {
        return describe;
    }

    public String getLabel() {
        return label;
    }

    /**
     * 根据值获取描述
     *
     * @param value
     * @return
     */
    public static String getDescByValue(Integer value) {
        for (ImportInfoTypeEnum item : ImportInfoTypeEnum.values()) {
            if (item.getValue().equals(value)) {
                return item.getDescribe();
            }
        }
        return "--";
    }

    /**
     * 根据值获取枚举项
     *
     * @param value
     * @return
     */
    public static ImportInfoTypeEnum getItemByValue(Integer value) {
        for (ImportInfoTypeEnum item : ImportInfoTypeEnum.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

}
