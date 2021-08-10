package com.yjtech.wisdom.tourism.decisionsupport.common.strategy;


import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.decisionsupport.base.service.TargetQueryService;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 基础策略
 *
 * @author renguangqian
 * @date 2021/8/5 10:46
 */
@Component
public abstract class BaseStrategy {

    private static final int HUNDRED = 100;

    private static final String DEFAULT_STR = "-";

    @Autowired
    private TargetQueryService targetQueryService;

    /**
     * 初始化方法
     *
     * @return
     */
    public Object init(){return null;}

    /**
     * 初始化方法
     *
     * @return
     */
    public Object init(DecisionEntity entity){return null;}

    /**
     * 初始化方法 - 综合概况
     *
     * @return
     */
    public Object init(List<DecisionWarnEntity> list, DecisionEntity entity){return null;}


    /**
     * 获取统计年月
     *
     * @return
     */
    protected String getCurrentLastMonthStr() {
        return DateTimeUtil.getCurrentLastMonthStr();
    }

    /**
     * 获取平台简称
     *
     * @return
     */
    protected String getPlatformSimpleName() {
        return targetQueryService.queryPlatformSimpleName().getSimpleName();
    }

    /**
     * 文本报警处理
     *
     * @param decisionInfo
     * @param entity
     */
    protected void textAlarmDeal(DecisionEntity decisionInfo, DecisionWarnEntity entity, String queryResult) {
        // 文本
        if (DecisionSupportConstants.DECISION_WARN_TYPE_TEXT.equals(decisionInfo.getRiskType())
                && !StringUtils.isEmpty(queryResult)) {

            switch (decisionInfo.getRiskType()) {
                // 低风险
                case DecisionSupportConstants.LOW_RISK_TYPE:
                    entity.setAlarmTypeText(DecisionSupportConstants.LOW_RISK_TYPE_TEXT);
                    break;

                // 中风险
                case DecisionSupportConstants.MEDIUM_RISK_TYPE:
                    entity.setAlarmTypeText(DecisionSupportConstants.LOW_RISK_TYPE_TEXT);
                    break;

                // 高风险
                case DecisionSupportConstants.HIGH_RISK_TYPE:
                    entity.setAlarmTypeText(DecisionSupportConstants.LOW_RISK_TYPE_TEXT);
                    break;

                // 无风险
                default:
                    break;
            }
        }
    }

    /**
     * 数值类报警处理
     *
     * @param decisionInfo
     * @param entity
     * @param scale 百分比
     */
    protected void numberAlarmDeal(DecisionEntity decisionInfo, DecisionWarnEntity entity, String scale) {
        if (DEFAULT_STR.equals(scale)) {
            return;
        }
        // 数值
        if (DecisionSupportConstants.DECISION_WARN_TYPE_NUMBER.equals(decisionInfo.getConfigType())) {
            switch (decisionInfo.getChangeType()) {
                // 上升
                case DecisionSupportConstants.NUMBER_TYPE_UP:
                    numberAlarmHandle(decisionInfo, entity, scale, DecisionSupportConstants.NUMBER_TYPE_UP_SIGN);
                    break;

                // 下降
                case DecisionSupportConstants.NUMBER_TYPE_DOWN:
                    numberAlarmHandle(decisionInfo, entity, scale, DecisionSupportConstants.NUMBER_TYPE_DOWN_SIGN);
                    break;

                // 忽略变化 不做处理
                default:
                    break;
            }
        }
    }

    /**
     * 数值类型处理方式
     *
     * @param decisionInfo
     * @param entity
     * @param targetNumber
     * @param sign
     */
    private void numberAlarmHandle(DecisionEntity decisionInfo, DecisionWarnEntity entity, String targetNumber, int sign) {
        // 低风险预警值
        Double lowRiskThreshold = decisionInfo.getLowRiskThreshold() * HUNDRED * sign;
        // 中风险预警值
        Double mediumRiskThreshold = decisionInfo.getMediumRiskThreshold() * HUNDRED * sign;
        // 高风险预警值
        Double highRiskThreshold = decisionInfo.getHighRiskThreshold() * HUNDRED * sign;

        double tbValue = Double.parseDouble(targetNumber);

        // 低风险
        if (tbValue > lowRiskThreshold && tbValue < mediumRiskThreshold) {
            entity.setAlarmTypeText(DecisionSupportConstants.LOW_RISK_TYPE_TEXT);
            entity.setAlarmType(DecisionSupportConstants.LOW_RISK_TYPE);
        }
        // 中风险
        else if (tbValue > mediumRiskThreshold && tbValue < highRiskThreshold) {
            entity.setAlarmTypeText(DecisionSupportConstants.MEDIUM_RISK_TYPE_TEXT);
            entity.setAlarmType(DecisionSupportConstants.MEDIUM_RISK_TYPE);
        }
        // 高风险
        else if (tbValue > highRiskThreshold) {
            entity.setAlarmTypeText(DecisionSupportConstants.HIGH_RISK_TYPE_TEXT);
            entity.setAlarmType(DecisionSupportConstants.HIGH_RISK_TYPE);
        }
        // 无风险 则不需要报警
        else {
            // not do someThing
        }
    }

    /**
     * 计算比例
     *
     * @param cs 除数
     * @param bcs 被除数
     */
    protected String computeScale(Integer cs, Integer bcs) {
        if (0 != cs) {
            // 比例 = （本月生成 - 上月生成 ）/ 本月生成
            return MathUtil.calPercent(new BigDecimal(cs - bcs), new BigDecimal(cs), 2).toString();
        }
        return "-";
    }
}
