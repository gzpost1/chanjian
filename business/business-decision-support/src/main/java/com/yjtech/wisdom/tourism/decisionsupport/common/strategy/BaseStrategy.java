package com.yjtech.wisdom.tourism.decisionsupport.common.strategy;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.constant.MockDataConstant;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.decisionsupport.base.service.TargetQueryService;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnEntity;
import com.yjtech.wisdom.tourism.dto.MonthPassengerFlowDto;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDictData;
import com.yjtech.wisdom.tourism.mybatis.utils.AnalysisUtils;
import com.yjtech.wisdom.tourism.service.impl.DistrictTourImplService;
import com.yjtech.wisdom.tourism.system.service.SysDictTypeService;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.SimulationQueryDto;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.decisionsupport.DecisionMockDTO;
import com.yjtech.wisdom.tourism.systemconfig.simulation.service.SimulationConfigService;
import com.yjtech.wisdom.tourism.vo.PassengerFlowVo;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基础策略
 *
 * @author renguangqian
 * @date 2021/8/5 10:46
 */
@Component
public abstract class BaseStrategy implements ApplicationListener {

    private static final int HUNDRED = 100;

    private static final String DEFAULT_STR = "-";

    /**
     * 风险等级缓存
     */
    protected static HashMap<String, Object> riskTypeMap;

    /**
     * 决策辅助-模拟规则
     */
    protected static List<DecisionMockDTO> mockRuleData;

    @Autowired
    private TargetQueryService targetQueryService;

    @Autowired
    private DistrictTourImplService districtTourService;

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
    public Object init(DecisionEntity entity, Integer isSimulation){return null;}

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
        textAlarmDeal(decisionInfo, entity, queryResult, DecisionSupportConstants.IMPL);
    }

    /**
     * 文本报警处理
     *
     * @param decisionInfo
     * @param entity
     */
    protected void textAlarmDeal(DecisionEntity decisionInfo, DecisionWarnEntity entity, String queryResult, Integer isSimulation) {

        // 模拟数据
        if (DecisionSupportConstants.MOCK.equals(isSimulation) && !MapUtils.isEmpty(riskTypeMap)) {
            if (!ObjectUtils.isEmpty(mockRuleData)) {
                for (DecisionMockDTO data: mockRuleData) {
                    riskTypeMap.forEach((k, v) -> {
                        if (data.getValue().equals(v)) {
                            entity.setAlarmType(Integer.parseInt(data.getValue()));
                            entity.setAlarmTypeText(k);
                        }
                    });
                }
            }
            return;
        }

        // 文本
        if (DecisionSupportConstants.DECISION_WARN_TYPE_TEXT.equals(decisionInfo.getRiskType())
                && !StringUtils.isEmpty(queryResult)) {

            switch (decisionInfo.getRiskType()) {
                // 低风险
                case DecisionSupportConstants.LOW_RISK_TYPE:
                    entity.setAlarmTypeText(DecisionSupportConstants.LOW_RISK_TYPE_TEXT);
                    entity.setAlarmType(DecisionSupportConstants.LOW_RISK_TYPE);
                    break;

                // 中风险
                case DecisionSupportConstants.MEDIUM_RISK_TYPE:
                    entity.setAlarmTypeText(DecisionSupportConstants.MEDIUM_RISK_TYPE_TEXT);
                    entity.setAlarmType(DecisionSupportConstants.MEDIUM_RISK_TYPE);
                    break;

                // 高风险
                case DecisionSupportConstants.HIGH_RISK_TYPE:
                    entity.setAlarmTypeText(DecisionSupportConstants.HIGH_RISK_TYPE_TEXT);
                    entity.setAlarmType(DecisionSupportConstants.HIGH_RISK_TYPE);
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
        numberAlarmDeal(decisionInfo, entity, scale, DecisionSupportConstants.IMPL);
    }

    /**
     * 数值类报警处理
     *
     * @param decisionInfo
     * @param entity
     * @param scale 百分比
     */
    protected void numberAlarmDeal(DecisionEntity decisionInfo, DecisionWarnEntity entity, String scale, Integer isSimulation) {
        // 模拟数据
        if (DecisionSupportConstants.MOCK.equals(isSimulation) && !MapUtils.isEmpty(riskTypeMap)) {
            if (!ObjectUtils.isEmpty(mockRuleData)) {
                for (DecisionMockDTO data: mockRuleData) {
                    riskTypeMap.forEach((k, v) -> {
                        if (data.getValue().equals(v)) {
                            entity.setAlarmType(Integer.parseInt(data.getValue()));
                            entity.setAlarmTypeText(k);
                        }
                    });
                }
            }
            return;
        }

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
     * @param bcs 除数
     * @param cs 被除数
     */
    protected String computeScale(Integer bcs, Integer cs) {
        if (0 != bcs) {
            // 比例 = （本月生成 - 上月生成 ）/ 本月生成
            return MathUtil.calPercent(new BigDecimal(bcs - cs), new BigDecimal(bcs), 1).toString();
        }
        return "-";
    }
    /**
     * 计算比例
     *
     * @param bcs 除数
     * @param cs 被除数
     */
    protected String computeScale(Double bcs, Double cs) {
        if (0 != bcs) {
            // 比例 = （本月生成 - 上月生成 ）/ 本月生成
            return MathUtil.calPercent(new BigDecimal(bcs - cs), new BigDecimal(bcs), 1).toString();
        }
        return "-";
    }

    /**
     * 设置比例
     *
     * @param scale
     * @return
     */
    protected static String getScale(String scale) {
        if (StringUtils.isEmpty(scale)
                || DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(scale)
                || DecisionSupportConstants.ZERO.equals(scale)
                || DecisionSupportConstants.NULL.equals(scale)) {
            return DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;
        }
        double scaleDouble;
        try {
            scaleDouble = new BigDecimal(scale).divide(new BigDecimal(1), 1).doubleValue();
        }catch (Exception e) {
            return DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;
        }
        return scaleDouble > DecisionSupportConstants.ZERO_NUMBER ?
                DecisionSupportConstants.ADD + scale  + DecisionSupportConstants.PERCENT
                : DecisionSupportConstants.REDUCE + scale + DecisionSupportConstants.PERCENT;
    }

    /**
     * 获取图表数据
     *
     * @return
     */
    protected List<BaseValueVO> getProvinceCharData(String statisticsType, Integer isSimulation) {
        String beginDate = DateTimeUtil.getCurrentYearStr() + DecisionSupportConstants.START_DATE_STR;
        String endTime = DateTimeUtil.getCurrentYearStr() + DecisionSupportConstants.END_DATE_STR;

        // 请求参数构造
        PassengerFlowVo yearPassengerFlowVo = new PassengerFlowVo();
        yearPassengerFlowVo.setIsSimulation(isSimulation);
        yearPassengerFlowVo.setStatisticsType(statisticsType);
        yearPassengerFlowVo.setBeginTime(DateTimeUtil.getLocalDateTime(beginDate));
        yearPassengerFlowVo.setEndTime(DateTimeUtil.getLocalDateTime(endTime));
        yearPassengerFlowVo.setType(DecisionSupportConstants.YEAR_MONTH);
        List<MonthPassengerFlowDto> yearPassengerFlowDtos = districtTourService.queryYearPassengerFlow(yearPassengerFlowVo);
        yearPassengerFlowDtos = yearPassengerFlowDtos.stream().map(v -> {
            v.setTime(v.getDate());
            return v;
        }).collect(Collectors.toList());
        return AnalysisUtils.MultipleBuildAnalysis(
                yearPassengerFlowVo,
                yearPassengerFlowDtos,
                true,
                MonthPassengerFlowDto::getNumber, MonthPassengerFlowDto::getTbNumber, MonthPassengerFlowDto::getHbScale, MonthPassengerFlowDto::getTbScale);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

    }
}
