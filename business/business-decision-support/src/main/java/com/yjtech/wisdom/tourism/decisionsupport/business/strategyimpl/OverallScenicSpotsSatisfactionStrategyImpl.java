package com.yjtech.wisdom.tourism.decisionsupport.business.strategyimpl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportTargetConstants;
import com.yjtech.wisdom.tourism.common.enums.DecisionSupportConfigEnum;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.decisionsupport.business.dto.StatisticsDto;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnEntity;
import com.yjtech.wisdom.tourism.decisionsupport.common.strategy.BaseStrategy;
import com.yjtech.wisdom.tourism.decisionsupport.common.util.PlaceholderUtils;
import com.yjtech.wisdom.tourism.dto.MonthPassengerFlowDto;
import com.yjtech.wisdom.tourism.dto.RankingDto;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicScreenQuery;
import com.yjtech.wisdom.tourism.resource.scenic.service.ScenicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 整体景区满意度 - pass
 *
 * @author renguangqian
 * @date 2021/8/9 10:04
 */
@Component
public class OverallScenicSpotsSatisfactionStrategyImpl extends BaseStrategy {

    @Autowired
    private ScenicService scenicService;

    /**
     * 整体景区满意度
     *
     * @param entity
     * @param isSimulation
     * @return
     */
    @Override
    public DecisionWarnEntity init(DecisionEntity entity, Integer isSimulation) {

        DecisionWarnEntity result = JSONObject.parseObject(JSONObject.toJSONString(entity), DecisionWarnEntity.class);

        int configId = entity.getConfigId().intValue();

        // 统计年月
        String currentLastMonthStr = super.getCurrentLastMonthStr();

        // 当年 上月
        LocalDateTime startDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastMonthStr() + DecisionSupportConstants.START_DAY_STR);
        LocalDateTime endDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastMonthStr() + DecisionSupportConstants.END_DAY_STR);

        // 当年 上上月
        LocalDateTime starLastDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastLastMonthStr() + DecisionSupportConstants.START_DAY_STR);
        LocalDateTime endLastDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastLastMonthStr() + DecisionSupportConstants.END_DAY_STR);

        // 去年 上月
        LocalDateTime startLastYearDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getLastYearLastMonthStr() + DecisionSupportConstants.START_DAY_STR);
        LocalDateTime endLastYearDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getLastYearLastMonthStr() + DecisionSupportConstants.END_DAY_STR);

        // 上月
        StatisticsDto lastMonthSatisfaction = getSatisfaction(startDate, endDate);

        // 上上月
        StatisticsDto lastLastMonthSatisfaction = getSatisfaction(starLastDate, endLastDate);

       // 去年同月
        StatisticsDto lastYearLastMonthSatisfaction = getSatisfaction(startLastYearDate, endLastYearDate);

        // 满意度 同比、环比
        String hb = "-";
        String tb = "-";

        if (!DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(lastLastMonthSatisfaction.getSatisfaction())) {
            hb = computeScale(Double.parseDouble(lastMonthSatisfaction.getSatisfaction()), Double.parseDouble(lastLastMonthSatisfaction.getSatisfaction()));
            tb = computeScale(Double.parseDouble(lastMonthSatisfaction.getSatisfaction()), Double.parseDouble(lastYearLastMonthSatisfaction.getSatisfaction()));
        }

        // 评价数量 环比
        String evaluateHb = "-";
        if (!DecisionSupportConstants.ZERO_NUMBER.equals(lastLastMonthSatisfaction.getTotal())) {
            evaluateHb = computeScale(lastMonthSatisfaction.getTotal(), lastLastMonthSatisfaction.getTotal());
        }

        // 好评数量 环比
        String goodEvaluateHb = "-";
        if (!DecisionSupportConstants.ZERO_NUMBER.equals(lastLastMonthSatisfaction.getGoodTotal())) {
            evaluateHb = computeScale(lastMonthSatisfaction.getGoodTotal(), lastLastMonthSatisfaction.getGoodTotal());
        }

        // 图标数据：景区评价分布
        result.setChartData(getCharData(startDate, endDate));

        // 处理指标报警
        switch (configId) {

            // 整体景区满意度 _统计年月 （文本）
            case DecisionSupportConstants.ZTJQMYD_TJNY :
                result.setWarnNum(currentLastMonthStr);
                textAlarmDeal(entity, result, currentLastMonthStr, isSimulation);
                break;

            // 整体景区满意度 _整体景区评价数量 （数值）
            case DecisionSupportConstants.ZTJQMYD_ZTJQPJSL :
                result.setWarnNum(String.valueOf(lastMonthSatisfaction.getTotal()));
                numberAlarmDeal(entity, result, evaluateHb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_NUMBER_VALUE.equals(lastMonthSatisfaction.getTotal())) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 整体景区满意度 _整体景区好评数量 （数值）
            case DecisionSupportConstants.ZTJQMYD_ZTJQHPSL :
                result.setWarnNum(String.valueOf(lastMonthSatisfaction.getGoodTotal()));
                numberAlarmDeal(entity, result, goodEvaluateHb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_NUMBER_VALUE.equals(lastMonthSatisfaction.getGoodTotal())) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 整体景区满意度 _整体景区满意度 （数值）
            case DecisionSupportConstants.ZTJQMYD_ZTJQMYD :
                result.setWarnNum(String.valueOf(lastMonthSatisfaction.getSatisfaction()));
                numberAlarmDeal(entity, result, hb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(lastMonthSatisfaction.getSatisfaction())) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 整体景区满意度 _环比变化（较上月） （数值）
            case DecisionSupportConstants.ZTJQMYD_HBBH :
                result.setWarnNum(hb);
                numberAlarmDeal(entity, result, hb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(hb)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 整体景区满意度 _同比变化（较去年同月） （数值）
            case DecisionSupportConstants.ZTJQMYD_TBBH :
                result.setWarnNum(tb);
                numberAlarmDeal(entity, result, tb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(tb)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            default:
                break;
        }

        // 处理话术
        String conclusionText = entity.getConclusionText();
        if (!StringUtils.isEmpty(conclusionText)) {
            conclusionText = PlaceholderUtils.replace(conclusionText,
                    DecisionSupportConfigEnum.YEAR_MONTH_STATISTICAL.getKey(), currentLastMonthStr + DecisionSupportConstants.MONTH,
                    DecisionSupportConfigEnum.OVERALL_SCENIC_SPOT_EVALUATIONS_NUMBER.getKey(), lastMonthSatisfaction.getTotal() + "",
                    DecisionSupportConfigEnum.OVERALL_SCENIC_SPOT_POSITIVE_EVALUATIONS_NUMBER.getKey(), lastMonthSatisfaction.getGoodTotal() + "",
                    DecisionSupportConfigEnum.OVERALL_SCENIC_SPOT_SATISFACTION.getKey(), lastMonthSatisfaction.getSatisfaction() + DecisionSupportConstants.PERCENT,
                    DecisionSupportConfigEnum.HB.getKey(), getScale(hb),
                    DecisionSupportConfigEnum.TB.getKey(), getScale(tb));
            result.setConclusionText(conclusionText);
        }

        // 设置月环比
        result.setMonthHbScale(hb);
        return result;
    }

    /**
     * 图标数据：景区评价分布
     *
     * @param startDate
     * @return
     */
    private List getCharData(LocalDateTime startDate, LocalDateTime endDate) {
        ScenicScreenQuery vo = new ScenicScreenQuery();
        vo.setBeginTime(startDate);
        vo.setEndTime(endDate);
        // 1：景区
        vo.setDataType((byte)1);
        return scenicService.queryEvaluateTypeDistribution(vo);
    }

    /**
     * 获取评价
     *
     * @param startDate
     * @param endDate
     * @return
     */
    private StatisticsDto getSatisfaction (LocalDateTime startDate, LocalDateTime endDate) {
       return getSatisfaction(startDate, endDate, null);
    }

    /**
     * 获取评价
     *
     * @param startDate
     * @param endDate
     * @param type
     * @return
     */
    private StatisticsDto getSatisfaction (LocalDateTime startDate, LocalDateTime endDate, Byte type) {
        // 整体景区评价数量、好评数量、整体景区满意度
        ScenicScreenQuery vo = new ScenicScreenQuery();
        vo.setBeginTime(startDate);
        vo.setEndTime(endDate);
        // 1:景点
        vo.setDataType((byte)1);
        if (type != null) {
            vo.setEvaluateType(type);
        }
        MarketingEvaluateStatisticsDTO marketingEvaluateStatisticsDTO = scenicService.queryScenicEvaluateStatistics(vo);

        int total = 0;
        int good = 0;
        String satisfaction = "-";
        if (!ObjectUtils.isEmpty(marketingEvaluateStatisticsDTO.getEvaluateTotal())) {
            total = marketingEvaluateStatisticsDTO.getEvaluateTotal();
        }
        if (0 != marketingEvaluateStatisticsDTO.getSatisfaction().intValue()) {
            satisfaction = marketingEvaluateStatisticsDTO.getSatisfaction().toString();
            good = marketingEvaluateStatisticsDTO.getSatisfaction().divide(new BigDecimal(100), 0).multiply(new BigDecimal(total)).intValue();
        }

        return StatisticsDto.builder().total(total).goodTotal(good).satisfaction(satisfaction).build();
    }

}
