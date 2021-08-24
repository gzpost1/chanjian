package com.yjtech.wisdom.tourism.decisionsupport.business.strategyimpl;

import com.alibaba.fastjson.JSONObject;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.enums.DecisionSupportConfigEnum;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.decisionsupport.business.dto.StatisticsDto;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnEntity;
import com.yjtech.wisdom.tourism.decisionsupport.common.strategy.BaseStrategy;
import com.yjtech.wisdom.tourism.decisionsupport.common.util.PlaceholderUtils;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateQueryVO;
import com.yjtech.wisdom.tourism.marketing.service.MarketingEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 整体酒店民宿满意度 -pass
 *
 * @author renguangqian
 * @date 2021/8/9 10:11
 */
@Component
public class OverallHotelHomestaySatisfactionStrategyImpl extends BaseStrategy {

    @Autowired
    private MarketingEvaluateService marketingEvaluateService;

    /**
     * 整体酒店民宿满意度
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
        StatisticsDto lastMonth = getSatisfaction(startDate, endDate, isSimulation);

        // 上上月
        StatisticsDto lastLastMonth = getSatisfaction(starLastDate, endLastDate, isSimulation);

        // 去年同月
        StatisticsDto lastYearLastMonth = getSatisfaction(startLastYearDate, endLastYearDate, isSimulation);

        // 评价 环比
        String evaluateHb = DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;
        // 好评 环比
        String goodEvaluateHb = DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;
        // 满意度 环比
        String hb = DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;
        // 满意度 同比
        String tb = DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;

        // 评价环比
        if (!DecisionSupportConstants.ZERO_NUMBER.equals(lastLastMonth.getTotal())) {
            // 环比
            evaluateHb = MathUtil.calPercent(new BigDecimal(lastMonth.getTotal() - lastLastMonth.getTotal()), new BigDecimal(lastMonth.getTotal()), 2).toString();
        }

        // 好评环比
        if (!DecisionSupportConstants.ZERO_NUMBER.equals(lastLastMonth.getGoodTotal())) {
            goodEvaluateHb = MathUtil.calPercent(new BigDecimal(lastMonth.getGoodTotal() - lastLastMonth.getGoodTotal()), new BigDecimal(lastMonth.getGoodTotal()), 2).toString();
        }

        // 满意度环比
        if (!DecisionSupportConstants.ZERO.equals(lastLastMonth.getSatisfaction())) {
            hb = MathUtil.calPercent(new BigDecimal(Double.parseDouble(lastMonth.getSatisfaction()) - Double.parseDouble(lastLastMonth.getSatisfaction())), new BigDecimal(lastMonth.getSatisfaction()), 2).toString();
        }

        // 满意度同比
        if (!DecisionSupportConstants.ZERO.equals(lastYearLastMonth.getSatisfaction())) {
            tb = MathUtil.calPercent(new BigDecimal(Double.parseDouble(lastMonth.getSatisfaction()) - Double.parseDouble(lastYearLastMonth.getSatisfaction())), new BigDecimal(lastMonth.getSatisfaction()), 2).toString();
        }

        // 图表数据：评价类型分布
        result.setChartData(getCharData(startDate, endDate, isSimulation));

        // 处理指标报警
        switch (configId) {

            // 整体酒店民宿满意度 _统计年月 （文本）
            case DecisionSupportConstants.ZTJDMSMYD_TJNY :
                result.setWarnNum(currentLastMonthStr);
                textAlarmDeal(entity, result, currentLastMonthStr, isSimulation);
                break;

            // 整体酒店民宿满意度 _整体酒店民宿评价数量 （数值）
            case DecisionSupportConstants.ZTJDMSMYD_ZTJDMSPJSL :
                result.setWarnNum(String.valueOf(lastMonth.getTotal()));
                numberAlarmDeal(entity, result, evaluateHb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_NUMBER_VALUE.equals(lastMonth.getTotal())) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 整体酒店民宿满意度 _整体酒店民宿好评数量 （数值）
            case DecisionSupportConstants.ZTJDMSMYD_ZTJDMSHPSL :
                result.setWarnNum(String.valueOf(lastMonth.getGoodTotal()));
                numberAlarmDeal(entity, result, goodEvaluateHb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_NUMBER_VALUE.equals(lastMonth.getGoodTotal())) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 整体酒店民宿满意度 _整体酒店民宿满意度 （数值）
            case DecisionSupportConstants.ZTJDMSMYD_ZTJDMSMYD :
                result.setWarnNum(lastMonth.getSatisfaction());
                numberAlarmDeal(entity, result, hb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(lastMonth.getSatisfaction())) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 整体酒店民宿满意度 _环比变化（较上月） （数值）
            case DecisionSupportConstants.ZTJDMSMYD_HBBH :
                result.setWarnNum(hb);
                numberAlarmDeal(entity, result, hb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(hb)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 整体酒店民宿满意度 _同比变化（较去年同月） （数值）
            case DecisionSupportConstants.ZTJDMSMYD_TBBH :
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
                    DecisionSupportConfigEnum.OVERALL_HOTEL_AND_HOMESTAY_REVIEWS_NUMBER.getKey(), String.valueOf(lastMonth.getTotal()),
                    DecisionSupportConfigEnum.OVERALL_HOTEL_AND_HOMESTAY_GOOD_REVIEWS_NUMBER.getKey(), String.valueOf(lastMonth.getGoodTotal()),
                    DecisionSupportConfigEnum.OVERALL_HOTEL_AND_HOMESTAY_SATISFACTION.getKey(), lastMonth.getSatisfaction() + DecisionSupportConstants.PERCENT,
                    DecisionSupportConfigEnum.HB.getKey(), getScale(hb),
                    DecisionSupportConfigEnum.TB.getKey(), getScale(tb));
            result.setConclusionText(conclusionText);
        }

        // 设置月环比
        result.setMonthHbScale(hb);
        return result;
    }

    /**
     * 图表数据：酒店民宿 评价类型分布
     *
     * @param startDate
     * @param endDate
     * @return
     */
    private List getCharData(LocalDateTime startDate, LocalDateTime endDate, Integer isSimulation) {
        EvaluateQueryVO evaluateQueryVO = new EvaluateQueryVO();
        evaluateQueryVO.setIsSimulation(isSimulation.byteValue());
        evaluateQueryVO.setBeginTime(startDate);
        evaluateQueryVO.setEndTime(endDate);
        evaluateQueryVO.setStatus(DecisionSupportConstants.ENABLE);
        return marketingEvaluateService.queryEvaluateTypeDistribution(evaluateQueryVO);
    }

    /**
     * 获取评价
     *
     * @param startDate
     * @param endDate
     * @return
     */
    private StatisticsDto getSatisfaction (LocalDateTime startDate, LocalDateTime endDate, Integer isSimulation) {

        EvaluateQueryVO evaluateScreenQueryVO = new EvaluateQueryVO();
        evaluateScreenQueryVO.setBeginTime(startDate);
        evaluateScreenQueryVO.setEndTime(endDate);
        evaluateScreenQueryVO.setIsSimulation(isSimulation.byteValue());

        // 上月
        MarketingEvaluateStatisticsDTO lastMonth = marketingEvaluateService.queryEvaluateStatistics(evaluateScreenQueryVO);
        // 评价数量
        Integer evaluateTotal = lastMonth.getEvaluateTotal();
        // 满意度
        double satisfaction = lastMonth.getSatisfaction().doubleValue();
        // 好评数量 = 评价数量 * 满意度/100
        Integer goodEvaluateTotal = new BigDecimal(evaluateTotal).multiply(new BigDecimal(satisfaction)).divide(new BigDecimal(100), 0).intValue();

        return StatisticsDto.builder().total(evaluateTotal).goodTotal(goodEvaluateTotal).satisfaction(String.valueOf(satisfaction)).build();
    }
}
