package com.yjtech.wisdom.tourism.decisionsupport.business.strategyimpl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.enums.DecisionSupportConfigEnum;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.decisionsupport.business.dto.RankingDataDto;
import com.yjtech.wisdom.tourism.decisionsupport.business.dto.ScaleDto;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnEntity;
import com.yjtech.wisdom.tourism.decisionsupport.common.strategy.BaseStrategy;
import com.yjtech.wisdom.tourism.decisionsupport.common.util.PlaceholderUtils;
import com.yjtech.wisdom.tourism.dto.RankingDto;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.resource.scenic.entity.vo.ScenicBaseVo;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicScreenQuery;
import com.yjtech.wisdom.tourism.resource.scenic.service.ScenicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 景区满意度排行 -pass
 *
 * @author renguangqian
 * @date 2021/8/9 10:05
 */
@Component
public class OverallScenicSpotsSatisfactionRankingStrategyImpl extends BaseStrategy {

    @Autowired
    private ScenicService scenicService;

    /**
     * 景区满意度排行
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

        // 满意度下降最多
        List<RankingDto> satisfactionDownMax = getSatisfactionDownMax();

        // 满意度下降最多景区名称
        String downMaxName = "";
        // 满意度下降最多景区评价量
        int downMaxEvaluation = 0;
        // 满意度下降最多景区好评量
        int downMaxGoodEvaluation = 0;
        // 满意度下降最多景区满意度
        String downMaxSatisfaction = "-";
        // 去年同月 满意度下降最多景区满意度
        String lastDownMaxSatisfaction = "-";
        // 其他满意度下降景区名称
        String otherDownName = "";
        // 满意度 同比
        String tb = "-";
        // 满意度 环比
        String hb = "-";
        // 上上月 下降最多景区好评量
        int lastLastMonthDownMaxGoodEvaluation = 0;
        // 上上月 下降最多景区评量
        int lastLastMonthDownMaxEvaluation = 0;
        // 上上月 满意度下降最多景区满意度
        String lastLastMonthDownMaxSatisfaction = "-";

        // 获取数据
        if (!CollectionUtils.isEmpty(satisfactionDownMax)) {
            RankingDto rankingDto = satisfactionDownMax.get(0);
            downMaxName = rankingDto.getName();

            // 当年 上月
            LocalDateTime startDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastMonthStr() + DecisionSupportConstants.START_DAY_STR);
            LocalDateTime endDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastMonthStr() + DecisionSupportConstants.END_DAY_STR);

            // 当年 上上月
            LocalDateTime starLastDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastLastMonthStr() + DecisionSupportConstants.START_DAY_STR);
            LocalDateTime endLastDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastLastMonthStr() + DecisionSupportConstants.END_DAY_STR);

            ScenicScreenQuery vo = new ScenicScreenQuery();
            vo.setBeginTime(startDate);
            vo.setEndTime(endDate);
            // 1：景区
            vo.setDataType((byte)1);
            vo.setScenicId(rankingDto.getId());
            MarketingEvaluateStatisticsDTO evaluateStatisticsDTO = scenicService.queryScenicEvaluateStatistics(vo);

            downMaxEvaluation = evaluateStatisticsDTO.getEvaluateTotal();
            downMaxSatisfaction = evaluateStatisticsDTO.getSatisfaction().toString();
            lastDownMaxSatisfaction = rankingDto.getLastYearLastMonthSatisfaction();
            downMaxGoodEvaluation = new BigDecimal(downMaxEvaluation).multiply(new BigDecimal(downMaxSatisfaction)).divide(new BigDecimal(100), 0).intValue();
            otherDownName = setOtherDownName(satisfactionDownMax);
            tb = rankingDto.getTb();
            hb = rankingDto.getScale();

            // 上上月
            vo.setBeginTime(starLastDate);
            vo.setEndTime(endLastDate);
            MarketingEvaluateStatisticsDTO lastLastMonthEvaluateStatisticsDTO = scenicService.queryScenicEvaluateStatistics(vo);
            lastLastMonthDownMaxEvaluation = lastLastMonthEvaluateStatisticsDTO.getEvaluateTotal();
            lastLastMonthDownMaxSatisfaction = lastLastMonthEvaluateStatisticsDTO.getSatisfaction().toString();
            lastLastMonthDownMaxGoodEvaluation = new BigDecimal(lastLastMonthDownMaxEvaluation).multiply(new BigDecimal(lastLastMonthDownMaxSatisfaction)).divide(new BigDecimal(100), 0).intValue();
            rankingDto.getScale();
        }

        // 景区满意度月环比下降Top5
        result.setChartData(getCharData(satisfactionDownMax));

        // 处理指标报警
        switch (configId) {
            // 景区满意度排行 _统计年月 （文本）
            case DecisionSupportConstants.JQMYDPH_TJNY :
                result.setWarnNum(currentLastMonthStr);
                textAlarmDeal(entity, result, currentLastMonthStr, isSimulation);
                break;

            // 景区满意度排行 _满意度下降最多景区名称 （文本）
            case DecisionSupportConstants.JQMYDPH_MYDXJZDJQMC :
                result.setWarnNum(downMaxName);
                textAlarmDeal(entity, result, downMaxName, isSimulation);
                // 判断是否使用缺失话术
                if (StringUtils.isEmpty(downMaxName)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 景区满意度排行 _其他满意度下降景区名称 （文本）
            case DecisionSupportConstants.JQMYDPH_QTMYDXJJQMC :
                result.setWarnNum(otherDownName);
                textAlarmDeal(entity, result, otherDownName, isSimulation);
                // 判断是否使用缺失话术
                if (StringUtils.isEmpty(otherDownName)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 景区满意度排行 _满意度下降最多景区评价量 （数值）
            case DecisionSupportConstants.JQMYDPH_MYDXJZDJQPJL :
                String scale = computeScale(downMaxEvaluation, downMaxEvaluation);
                result.setWarnNum(String.valueOf(downMaxEvaluation));
                numberAlarmDeal(entity, result, scale, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_NUMBER_VALUE.equals(downMaxEvaluation)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 景区满意度排行 _满意度下降最多景区好评量 （数值）
            case DecisionSupportConstants.JQMYDPH_MYDXJZDJQHPL :
                String goodScale = computeScale(downMaxGoodEvaluation, lastLastMonthDownMaxGoodEvaluation);
                result.setWarnNum(String.valueOf(downMaxGoodEvaluation));
                numberAlarmDeal(entity, result, goodScale, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_NUMBER_VALUE.equals(downMaxGoodEvaluation)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 景区满意度排行 _满意度下降最多景区满意度 （数值）
            case DecisionSupportConstants.JQMYDPH_MYDXJZDJQMYD :
                String satisfactionScale = computeScale(Double.parseDouble(lastDownMaxSatisfaction),  Double.parseDouble(lastLastMonthDownMaxSatisfaction));
                result.setWarnNum(lastDownMaxSatisfaction);
                numberAlarmDeal(entity, result, satisfactionScale, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_NUMBER_VALUE.equals(lastDownMaxSatisfaction)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 景区满意度排行 _环比变化（较上月） （数值）
            case DecisionSupportConstants.JQMYDPH_HBBH :
                result.setWarnNum(hb);
                numberAlarmDeal(entity, result, hb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(hb)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 景区满意度排行 _同比变化（较去年同月） （数值）
            case DecisionSupportConstants.JQMYDPH_TBBH :
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
                    DecisionSupportConfigEnum.SATISFACTION_MOST_DECLINE_SCENIC_SPOT_NAME.getKey(), downMaxName,
                    DecisionSupportConfigEnum.SATISFACTION_MOST_DECLINE_SCENIC_SPOT_EVALUATION.getKey(), String.valueOf(downMaxEvaluation),
                    DecisionSupportConfigEnum.SATISFACTION_MOST_DECLINE_SCENIC_SPOT_GOOD_EVALUATION.getKey(), String.valueOf(downMaxGoodEvaluation),
                    DecisionSupportConfigEnum.SATISFACTION_MOST_DECLINE_SCENIC_SPOT_SATISFACTION.getKey(), downMaxSatisfaction + DecisionSupportConstants.PERCENT,
                    DecisionSupportConfigEnum.OTHER_SATISFACTION_MOST_DECLINE_SCENIC_SPOT_NAME.getKey(), otherDownName,
                    DecisionSupportConfigEnum.HB.getKey(), getScale(hb),
                    DecisionSupportConfigEnum.TB.getKey(), getScale(tb));
            result.setConclusionText(conclusionText);
        }

        // 设置月环比
        result.setMonthHbScale(hb);
        return result;
    }


    /**
     * 获取满意度
     *
     * @param startDate
     * @param endDate
     * @return
     */
    private List<ScenicBaseVo> getSatisfaction (LocalDateTime startDate, LocalDateTime endDate) {
        // 整体景区评价数量、好评数量、整体景区满意度
        ScenicScreenQuery vo = new ScenicScreenQuery();
        vo.setBeginTime(startDate);
        vo.setEndTime(endDate);
        // 1：景点
        vo.setDataType((byte)1);
        return scenicService.querySatisfactionTop5(vo).getRecords();
    }

    /**
     * 获取满意度下降最多的景区名称
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

        // 去年 上月
        LocalDateTime startLastYearDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getLastYearLastMonthStr() + DecisionSupportConstants.START_DAY_STR);
        LocalDateTime endLastYearDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getLastYearLastMonthStr() + DecisionSupportConstants.END_DAY_STR);

        // 上月 满意度 排行
        List<ScenicBaseVo> lastMonthRankings = getSatisfaction(startDate, endDate);
        // 上上月 满意度 排行
        List<ScenicBaseVo> lastLastMonthRankings = getSatisfaction(starLastDate, endLastDate);
        //去年同月 满意度 排行
        List<ScenicBaseVo> lastYearLastMonthRankings = getSatisfaction(startLastYearDate, endLastYearDate);

        TreeMap<Double, ScaleDto> map = Maps.newTreeMap();

        for (ScenicBaseVo lastMonthRanking : lastMonthRankings) {
            String lastName = lastMonthRanking.getName();
            double lastValue = Double.parseDouble(lastMonthRanking.getValue());

            for (ScenicBaseVo lastLastMonthRanking : lastLastMonthRankings) {
                String lastLastName = lastLastMonthRanking.getName();
                double lastLastValue = Double.parseDouble(lastLastMonthRanking.getValue());

                // 满意度下降景区
                if (lastName.equals(lastLastName) && lastValue < lastLastValue) {
                    // 满意度环比
                    double hb = Double.parseDouble(computeScale(lastValue, lastLastValue));
                    ScaleDto build = ScaleDto.builder().hb(String.valueOf(hb)).name(lastName).build();
                    // 上月满意度
                    build.setLastMonthSatisfaction(String.valueOf(lastLastValue));

                    // 去年同月
                    for (ScenicBaseVo lastYearLastMonth : lastYearLastMonthRankings) {
                        String lastYearLastMonthName = lastYearLastMonth.getName();
                        double lastYearLastMonthValue = Double.parseDouble(lastYearLastMonth.getValue());
                        if (lastName.equals(lastYearLastMonthName)) {
                            String tb = computeScale(lastValue, lastYearLastMonthValue);
                            // 满意度同比
                            build.setTb(tb);
                            // 上年 同月 满意度
                            build.setLastYearLastMonthSatisfaction(String.valueOf(lastYearLastMonthValue));
                        }
                    }
                    map.put(hb, build);
                }
            }
        }

        List<RankingDto> result = Lists.newArrayList();
        map.forEach((key, value) -> {
            // 设置数据
            for (ScenicBaseVo lastMonth : lastMonthRankings) {
                if (value.equals(lastMonth.getName())) {
                    result.add(RankingDto.builder()
                            .name(value.getName())
                            .scale(key.toString())
                            .tb(value.getTb())
                            .lastMonthSatisfaction(value.getLastMonthSatisfaction())
                            .lastLastMonthSatisfaction(value.getLastLastMonthSatisfaction())
                            .lastYearLastMonthSatisfaction(value.getLastYearLastMonthSatisfaction())
                            .id(lastMonth.getId())
                            .value(lastMonth.getValue())
                            .build());
                }
            }
        });
        return result;
    }

    /**
     * 设置 其他名称
     *
     * @param source
     */
    private String setOtherDownName(List<RankingDto> source) {
        StringBuilder result = new StringBuilder();
        if (source.size() > 1) {
            String sign = "、";
            int size = source.size() > 5 ? 5 : source.size();
            // 从第二个开始遍历
            for (int i = 1; i < size; i++) {
                if (i == size - 1) {
                    sign = "";
                }
                result.append(source.get(i).getName()).append(sign);
            }
        }
        return result.toString();
    }

    /**
     * 图表：景区客流月环比下降Top5
     *
     * @param rankingDtos
     * @return
     */
    private List getCharData(List<RankingDto> rankingDtos) {
        List<RankingDataDto> list = Lists.newArrayList();
        for (RankingDto v : rankingDtos) {
            double scale = Math.abs(Double.parseDouble(v.getScale()));
            list.add(RankingDataDto.builder()
                    .name(v.getName())
                    .value(String.valueOf(scale))
                    .build());
        }
        return list;
    }
}
