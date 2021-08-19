package com.yjtech.wisdom.tourism.decisionsupport.business.strategyimpl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.enums.DecisionSupportConfigEnum;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.JsonUtils;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.decisionsupport.business.dto.OneTravelNumberDto;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnEntity;
import com.yjtech.wisdom.tourism.decisionsupport.common.strategy.BaseStrategy;
import com.yjtech.wisdom.tourism.decisionsupport.common.util.PlaceholderUtils;
import com.yjtech.wisdom.tourism.dto.RankingDto;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.HotelEvaluateSatisfactionRankDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateQueryVO;
import com.yjtech.wisdom.tourism.marketing.service.MarketingEvaluateService;
import com.yjtech.wisdom.tourism.resource.scenic.entity.vo.ScenicBaseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 整体酒店民宿满意度排行 -pass
 *
 * @author renguangqian
 * @date 2021/8/9 10:12
 */
@Component
public class OverallHotelHomestaySatisfactionRankingStrategyImpl extends BaseStrategy {

    @Autowired
    private MarketingEvaluateService marketingEvaluateService;

    /**
     * 整体酒店民宿满意度排行
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

        LocalDateTime yearBeginDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentYearStr() + DecisionSupportConstants.START_DATE_STR);
        LocalDateTime yearEndDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentYearStr() + DecisionSupportConstants.END_DATE_STR);

        EvaluateQueryVO vo = new EvaluateQueryVO();
        vo.setBeginTime(yearBeginDate);
        vo.setEndTime(yearEndDate);

        String currentLastMonthStr1 = DateTimeUtil.getCurrentLastMonthStr();

        // 环比
        String hb = DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;
        // 同比
        String tb = DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;

        // 满意度下降 最多 酒店民宿名称
        List<RankingDto> satisfactionDownMax = getSatisfactionDownMax();
        String downMaxName = "";
        RankingDto maxDown = satisfactionDownMax.get(0);
        if (!CollectionUtils.isEmpty(satisfactionDownMax)) {
            downMaxName = maxDown.getName();
        }

        // 其他满意度下降酒店民宿名称 显示第2-5个；如果小于2个则不显示；多个以顿号“、”分割
        String otherDownName = setOtherDownName(satisfactionDownMax);

        // 本年满意度趋势
        List<AnalysisBaseInfo> analysisBaseInfos = marketingEvaluateService.queryEvaluateSatisfactionAnalysis(vo);
        for (AnalysisBaseInfo v : analysisBaseInfos) {
            List<OneTravelNumberDto> numberDtoList = JSONObject.parseArray(JSONObject.toJSONString(JsonUtils.getValueByKey(v, DecisionSupportConstants.DATA)), OneTravelNumberDto.class);

            for (OneTravelNumberDto numberDto : numberDtoList) {
                if (currentLastMonthStr1.equals(numberDto.getTime())) {
                    if (!StringUtils.isEmpty(numberDto.getHb())) {
                        hb = numberDto.getHb();
                    }
                    if (!StringUtils.isEmpty(numberDto.getTb())) {
                        tb = numberDto.getTb();
                    }
                }
            }
        }

        // 当年 上月
        LocalDateTime monthStartDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastMonthStr() + DecisionSupportConstants.START_DAY_STR);
        LocalDateTime monthEndDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastMonthStr() + DecisionSupportConstants.END_DAY_STR);

        // 当年 上上月
        LocalDateTime monthstarLastDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastLastMonthStr() + DecisionSupportConstants.START_DAY_STR);
        LocalDateTime monthendLastDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastLastMonthStr() + DecisionSupportConstants.END_DAY_STR);

        EvaluateQueryVO evaluateScreenQueryVO = new EvaluateQueryVO();
        evaluateScreenQueryVO.setBeginTime(monthStartDate);
        evaluateScreenQueryVO.setEndTime(monthEndDate);
        evaluateScreenQueryVO.setPlaceId(maxDown.getId().toString());
        // 获取 满意度下降最多的 酒店信息
        MarketingEvaluateStatisticsDTO maxDownHotel = marketingEvaluateService.queryEvaluateStatistics(evaluateScreenQueryVO);

        // 满意度下降最多酒店民宿评价量
        Integer maxDownEvaluateTotal = maxDownHotel.getEvaluateTotal();

        // 满意度下降最多酒店民宿满意度
        String maxDownSatisfaction = maxDownHotel.getSatisfaction().toString();

        evaluateScreenQueryVO.setEvaluateType((byte)2);
        MarketingEvaluateStatisticsDTO maxDownGoodHotel = marketingEvaluateService.queryEvaluateStatistics(evaluateScreenQueryVO);
        // 满意度下降最多酒店民宿好评量
        Integer maxDownGoodEvaluateTotal = maxDownGoodHotel.getEvaluateTotal();

        // 上上月 满意度下降最多的 酒店信息
        evaluateScreenQueryVO.setEvaluateType(null);
        evaluateScreenQueryVO.setBeginTime(monthstarLastDate);
        evaluateScreenQueryVO.setEndTime(monthendLastDate);

        MarketingEvaluateStatisticsDTO maxDownHotelLastLastMonth = marketingEvaluateService.queryEvaluateStatistics(evaluateScreenQueryVO);

        // 上上月 满意度下降最多酒店民宿评价量
        Integer maxDownEvaluateTotalLastLastMonth = maxDownHotelLastLastMonth.getEvaluateTotal();

        // 上上月 满意度下降最多酒店民宿满意度
        String maxDownSatisfactionLastLastMonth = maxDownHotelLastLastMonth.getSatisfaction().toString();

        evaluateScreenQueryVO.setEvaluateType((byte)2);
        MarketingEvaluateStatisticsDTO maxDownGoodHotelLastLastMonth = marketingEvaluateService.queryEvaluateStatistics(evaluateScreenQueryVO);
        // 上上月 满意度下降最多酒店民宿好评量
        Integer maxDownGoodEvaluateTotalLastLastMonth = maxDownGoodHotelLastLastMonth.getEvaluateTotal();

        // 处理指标报警
        switch (configId) {
            // 酒店民宿满意度排行 _统计年月 （文本）
            case DecisionSupportConstants.JDMSMYDPH_TJNY :
                result.setWarnNum(currentLastMonthStr);
                textAlarmDeal(entity, result, currentLastMonthStr);
                break;

            // 酒店民宿满意度排行 _其他满意度下降酒店民宿名称 （文本）
            case DecisionSupportConstants.JDMSMYDPH_QTMYDXJJDMSMC :
                result.setWarnNum(otherDownName);
                textAlarmDeal(entity, result, otherDownName);
                // 判断是否使用缺失话术
                if (StringUtils.isEmpty(otherDownName)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 酒店民宿满意度排行 _满意度下降最多酒店民宿名称 （文本）
            case DecisionSupportConstants.JDMSMYDPH_MYDXJZDJDMSMC :
                result.setWarnNum(downMaxName);
                textAlarmDeal(entity, result, downMaxName);
                // 判断是否使用缺失话术
                if (StringUtils.isEmpty(downMaxName)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 酒店民宿满意度排行 _满意度下降最多酒店民宿评价量 （数值）
            case DecisionSupportConstants.JDMSMYDPH_MYDXJZDJDMSPJL :
                String scale = MathUtil.calPercent(new BigDecimal(maxDownEvaluateTotal - maxDownEvaluateTotalLastLastMonth), new BigDecimal(maxDownEvaluateTotal), 2).toString();
                result.setWarnNum(maxDownEvaluateTotal.toString());
                numberAlarmDeal(entity, result, scale);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(maxDownEvaluateTotal)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 酒店民宿满意度排行 _满意度下降最多酒店民宿好评量 （数值）
            case DecisionSupportConstants.JDMSMYDPH_MYDXJZDJDMSHPL :
                String goodScale = MathUtil.calPercent(new BigDecimal(maxDownGoodEvaluateTotal - maxDownGoodEvaluateTotalLastLastMonth), new BigDecimal(maxDownGoodEvaluateTotal), 2).toString();
                result.setWarnNum(maxDownEvaluateTotal.toString());
                numberAlarmDeal(entity, result, goodScale);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(maxDownGoodEvaluateTotal)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 酒店民宿满意度排行 _满意度下降最多酒店民宿满意度 （数值）
            case DecisionSupportConstants.JDMSMYDPH_MYDXJZDJDMSMYD :
                String satisfactionScale = MathUtil.calPercent(new BigDecimal(Double.parseDouble(maxDownSatisfaction) - Double.parseDouble(maxDownSatisfactionLastLastMonth)), new BigDecimal(maxDownSatisfaction), 2).toString();
                result.setWarnNum(maxDownSatisfaction);
                numberAlarmDeal(entity, result, satisfactionScale);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(maxDownSatisfaction)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 酒店民宿满意度排行 _环比变化（较上月） （数值）
            case DecisionSupportConstants.JDMSMYDPH_HBBH :
                result.setWarnNum(hb);
                numberAlarmDeal(entity, result, hb);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(hb)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 酒店民宿满意度排行 _同比变化（较去年同月） （数值）
            case DecisionSupportConstants.JDMSMYDPH_TBBH :
                result.setWarnNum(tb);
                numberAlarmDeal(entity, result, tb);
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
                    DecisionSupportConfigEnum.MOST_SATISFACTION_DECLINE_HOTEL_AND_HOMESTAY_NAME.getKey(), String.valueOf(maxDown.getName()),
                    DecisionSupportConfigEnum.MOST_SATISFACTION_DECLINE_HOTEL_AND_HOMESTAY_EVALUATION.getKey(), String.valueOf(maxDownEvaluateTotal),
                    DecisionSupportConfigEnum.MOST_SATISFACTION_DECLINE_HOTEL_AND_HOMESTAY_GOOD_EVALUATION.getKey(), String.valueOf(maxDownGoodEvaluateTotal),
                    DecisionSupportConfigEnum.MOST_SATISFACTION_DECLINE_HOTEL_AND_HOMESTAY_SATISFACTION.getKey(), maxDownSatisfaction + DecisionSupportConstants.PERCENT,
                    DecisionSupportConfigEnum.OTHER_SATISFACTION_DECLINE_HOTEL_AND_HOMESTAY_NAME.getKey(), otherDownName,
                    DecisionSupportConfigEnum.HB.getKey(), getScale(hb),
                    DecisionSupportConfigEnum.TB.getKey(), getScale(tb));
            result.setConclusionText(conclusionText);
        }

        // 设置月环比
        result.setMonthHbScale(hb);
        return result;
    }

    /**
     * 设置满意度下降其他酒店民宿名称
     *
     * @param satisfactionDownMax
     */
    private String setOtherDownName(List<RankingDto> satisfactionDownMax) {
        StringBuilder otherDownName = new StringBuilder();
        if (satisfactionDownMax.size() > 1) {
            String sign = "、";
            int size = satisfactionDownMax.size() > 5 ? 5 : satisfactionDownMax.size();
            // 从第二个开始遍历
            for (int i = 1; i < size; i++) {
                if (i == size - 1) {
                    sign = "";
                }
                otherDownName.append(satisfactionDownMax.get(i).getName()).append(sign);
            }
        }
        return otherDownName.toString();
    }

    /**
     * 获取满意度下降最多的酒店民宿名称
     *
     * @return
     */
    private List<RankingDto> getSatisfactionDownMax() {

        // 当年 上月
        LocalDateTime startDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastMonthStr() + DecisionSupportConstants.START_DAY_STR);
        LocalDateTime endDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastMonthStr() + DecisionSupportConstants.END_DAY_STR);

        // 当年 上上月
        LocalDateTime starLastDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastLastMonthStr() + DecisionSupportConstants.START_DAY_STR);
        LocalDateTime endLastDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastLastMonthStr() + DecisionSupportConstants.END_DAY_STR);

        EvaluateQueryVO vo = new EvaluateQueryVO();

        // 上月
        vo.setBeginTime(startDate);
        vo.setEndTime(endDate);
        vo.setPageSize(500L);
        List<HotelEvaluateSatisfactionRankDTO> lastMonth = marketingEvaluateService.queryEvaluateSatisfactionRank(vo).getRecords();

        // 上上月
        vo.setBeginTime(starLastDate);
        vo.setEndTime(endLastDate);
        List<HotelEvaluateSatisfactionRankDTO> lastLastMonth = marketingEvaluateService.queryEvaluateSatisfactionRank(vo).getRecords();

        TreeMap<Double, String> map = Maps.newTreeMap();

        // 找最大值
        for (int i = 0; i < lastMonth.size(); i++) {
            for (BaseVO lastLast : lastLastMonth) {
                BaseVO last = lastMonth.get(i);
                if (last.getName().equals(lastLast.getName())) {
                    int lastMonthValue = Integer.parseInt(last.getValue());
                    int lastLastMonthValue = Integer.parseInt(lastLast.getValue());
                    if (lastMonthValue < lastLastMonthValue) {
                        double scale = MathUtil.calPercent(new BigDecimal(lastMonthValue - lastLastMonthValue), new BigDecimal(lastMonthValue), 2).doubleValue();
                        map.put(scale, last.getName());
                    }
                }
            }
        }

        List<RankingDto> result = Lists.newArrayList();
        map.forEach((key, value) -> {
            for (HotelEvaluateSatisfactionRankDTO last : lastMonth) {
                if (value.equals(last.getName())) {
                    result.add(RankingDto.builder().name(value).scale(key.toString()).id(last.getId()).value(last.getValue()).build());
                }
            }
        });
        return result;

    }
}


