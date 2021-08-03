package com.yjtech.wisdom.tourism.common.enums;

/**
 * 决策辅助-话术配置项
 *
 * @author
 */
public enum DecisionSupportEnum {

    /**
     * 统计年月
     */
    YEAR_MONTH_STATISTICAL("统计年月", "${YEAR_MONTH_STATISTICAL}"),

    /**
     * 平台简称
     */
    PLATFORM_SIMPLE_NAME("平台简称", "${PLATFORM_SIMPLE_NAME}"),

    /**
     * 省外游客数量
     */
    PROVINCE_OUTSIDE_TOUR_NUM("省外游客数量", "${PROVINCE_OUTSIDE_TOUR_NUM}"),

    /**
     * 环比变化（较上月）
     */
    PROVINCE_OUTSIDE_TOUR_HB("环比变化（较上月）", "${PROVINCE_OUTSIDE_TOUR_HB}"),

    /**
     * 同比（较去年同月）
     */
    PROVINCE_OUTSIDE_TOUR_TB("同比（较去年同月）", "${PROVINCE_OUTSIDE_TOUR_TB}"),

    /**
     * 平台名称
     */
    PLATFORM_NAME("平台简称", "${PLATFORM_NAME}"),

    /**
     * 风险预警项数量
     */
    RISK_WARNING_ITEMS_NUMBER("风险预警项数量", "${RISK_WARNING_ITEMS_NUMBER}"),

    /**
     * 高风险项数量
     */
    HIGH_RISK_WARNING_ITEMS_NUMBER("高风险项数量", "${HIGH_RISK_WARNING_ITEMS_NUMBER}"),

    /**
     * 中风险项数量
     */
    MIDDLE_RISK_WARNING_ITEMS_NUMBER("高风险项数量", "${MIDDLE_RISK_WARNING_ITEMS_NUMBER}"),

    /**
     * 低风险项数量
     */
    LOW_RISK_WARNING_ITEMS_NUMBER("低风险项数量", "${LOW_RISK_WARNING_ITEMS_NUMBER}"),

    /**
     * 高风险项指标项
     */
    HIGH_RISK_WARNING_ITEMS_TARGET("高风险项指标项", "${HIGH_RISK_WARNING_ITEMS_TARGET}"),

    /**
     * 中风险项指标项
     */
    MIDDLE_RISK_WARNING_ITEMS_TARGET("中风险项指标项", "${MIDDLE_RISK_WARNING_ITEMS_TARGET}"),

    /**
     * 低风险项指标项
     */
    LOW_RISK_WARNING_ITEMS_TARGET("低风险项指标项", "${LOW_RISK_WARNING_ITEMS_TARGET}"),

    /**
     * 省内游客数量
     */
    PROVINCE_INSIDE_TOUR_NUM("省内游客数量", "${PROVINCE_INSIDE_TOUR_NUM}"),

    /**
     * 整体游客数量
     */
    PROVINCE_ALL_TOUR_NUM("整体游客数量", "${PROVINCE_ALL_TOUR_NUM}"),

    /**
     * 全部景区接待数量
     */
    ALL_SCENIC_SPOTS_RECEPTIONS_NUMBER("全部景区接待数量", "${ALL_SCENIC_SPOTS_RECEPTIONS_NUMBER}"),

    /**
     * 游客流失最多景区名称
     */
    SCENIC_SPOT_MOST_TOURISTS_LOST_NAME("游客流失最多景区名称", "${SCENIC_SPOT_MOST_TOURISTS_LOST_NAME}"),

    /**
     * 游客流失最多景区接待量
     */
    TOURISTS_LARGEST_LOSS_SCENIC_RECEPTION_VOLUME("游客流失最多景区接待量", "${TOURISTS_LARGEST_LOSS_SCENIC_RECEPTION_VOLUME}"),

    /**
     * 其他游客流失景区名称
     */
    SCENIC_SPOT_MOST_OTHER_TOURISTS_LOST_NAME("其他游客流失景区名称", "${SCENIC_SPOT_MOST_OTHER_TOURISTS_LOST_NAME}"),

    /**
     * 整体景区评价数量
     */
    OVERALL_SCENIC_SPOT_EVALUATIONS_NUMBER("整体景区评价数量", "${ALL_SCENIC_SPOT_EVALUATIONS_NUMBER}"),

    /**
     * 整体景区好评数量
     */
    OVERALL_SCENIC_SPOT_POSITIVE_EVALUATIONS_NUMBER("整体景区好评数量", "${ALL_SCENIC_SPOT_POSITIVE_EVALUATIONS_NUMBER}"),

    /**
     * 满意度下降最多景区名称
     */
    SATISFACTION_MOST_DECLINE_SCENIC_SPOT_NAME("满意度下降最多景区名称", "${SATISFACTION_MOST_DECLINE_SCENIC_SPOT_NAME}"),

    /**
     * 满意度下降最多景区评价量
     */
    SATISFACTION_MOST_DECLINE_SCENIC_SPOT_EVALUATION("满意度下降最多景区评价量", "${SATISFACTION_MOST_DECLINE_SCENIC_SPOT_EVALUATION}"),

    /**
     * 满意度下降最多景区满意度
     */
    SATISFACTION_MOST_DECLINE_SCENIC_SPOT_SATISFACTION("满意度下降最多景区满意度", "${SATISFACTION_MOST_DECLINE_SCENIC_SPOT_SATISFACTION}"),

    /**
     * 其他满意度下降景区名称
     */
    OTHER_SATISFACTION_MOST_DECLINE_SCENIC_SPOT_NAME("其他满意度下降景区名称", "${OTHER_SATISFACTION_MOST_DECLINE_SCENIC_SPOT_NAME}"),

    ;
    /**
     * 配置项名称
     */
    private String name;

    /**
     * 配置项 key
     */
    private String key;

    DecisionSupportEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }}
