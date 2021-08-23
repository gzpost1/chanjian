package com.yjtech.wisdom.tourism.decisionsupport.business.strategyimpl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.enums.DecisionSupportConfigEnum;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.decisionsupport.business.dto.RankingDataDto;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnEntity;
import com.yjtech.wisdom.tourism.decisionsupport.common.strategy.BaseStrategy;
import com.yjtech.wisdom.tourism.decisionsupport.common.util.PlaceholderUtils;
import com.yjtech.wisdom.tourism.dto.MonthPassengerFlowDto;
import com.yjtech.wisdom.tourism.dto.RankingDto;
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
 * 景区客流排行 -pass
 *
 * @author renguangqian
 * @date 2021/8/9 10:02
 */
@Component
public class OverallScenicSpotsTouristFlowRankingStrategyImpl extends BaseStrategy {

    @Autowired
    private ScenicService scenicService;

    /**
     * 景区客流排行
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

        // 游客排行
        List<RankingDto> tourDownMax = getSatisfactionDownMax();

        // 游客流失最多景区名称
        String downMaxName = "";
        // 游客流失最多景区接待量
        String downMaxReception = "";
        // 其他游客流失景区名称
        String otherDownName = "";

        // 获取数据
        if (!CollectionUtils.isEmpty(tourDownMax)) {
            RankingDto rankingDto = tourDownMax.get(0);
            downMaxName = rankingDto.getName();
            downMaxReception = rankingDto.getValue();
            otherDownName = setOtherDownName(tourDownMax);
        }

        // 客流趋势
        ScenicScreenQuery query = new ScenicScreenQuery();
        query.setBeginTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentYearStr() + DecisionSupportConstants.START_DATE_STR));
        query.setEndTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentYearStr() + DecisionSupportConstants.END_DATE_STR));
        query.setType(DecisionSupportConstants.YEAR_MONTH);
        List<MonthPassengerFlowDto> monthPassengerFlowDtos = scenicService.queryPassengerFlowTrend(query);

        String tb = DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;
        String hb = DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;

        // 上月日期
        String currentLastMonth = DateTimeUtil.getCurrentLastMonthStr();
        for (MonthPassengerFlowDto v : monthPassengerFlowDtos) {
            if (currentLastMonth.equals(v.getDate())) {
                tb = v.getTbScale();
                hb = v.getHbScale();
            }
        }

        // 图标：景区客流月环比下降Top5
        result.setChartData(getCharData(tourDownMax));

        // 处理指标报警
        switch (configId) {

            // 景区客流排行 _统计年月 （文本）
            case DecisionSupportConstants.JQKLPH_TJNY :
                result.setWarnNum(currentLastMonthStr);
                textAlarmDeal(entity, result, currentLastMonthStr);
                break;

            // 景区客流排行 _游客流失最多景区名称 （文本）
            case DecisionSupportConstants.JQKLPH_YKLSZDJQMC :
                result.setWarnNum(downMaxName);
                textAlarmDeal(entity, result, downMaxName);
                // 判断是否使用缺失话术
                if (StringUtils.isEmpty(downMaxName)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 景区客流排行 _其他游客流失景区名称 （文本）
            case DecisionSupportConstants.JQKLPH_QTYKLSJQMC :
                result.setWarnNum(otherDownName);
                textAlarmDeal(entity, result, otherDownName);
                // 判断是否使用缺失话术
                if (StringUtils.isEmpty(otherDownName)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 景区客流排行 _游客流失最多景区接待量 （数值）
            case DecisionSupportConstants.JQKLPH_YKLSZDJQJDL :
                result.setWarnNum(downMaxReception);
                numberAlarmDeal(entity, result, hb);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_NUMBER_VALUE.equals(Integer.parseInt(downMaxReception))) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 景区客流排行 _环比变化（较上月） （数值）
            case DecisionSupportConstants.JQKLPH_HBBH :
                result.setWarnNum(hb);
                numberAlarmDeal(entity, result, hb);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(hb)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 景区客流排行 _同比变化（较去年同月） （数值）
            case DecisionSupportConstants.JQKLPH_TBBH :
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
                    DecisionSupportConfigEnum.SCENIC_SPOT_MOST_TOURISTS_LOST_NAME.getKey(), downMaxName,
                    DecisionSupportConfigEnum.SCENIC_SPOT_MOST_OTHER_TOURISTS_LOST_NAME.getKey(), otherDownName,
                    DecisionSupportConfigEnum.TOURISTS_LARGEST_LOSS_SCENIC_RECEPTION_VOLUME.getKey(), downMaxReception,
                    DecisionSupportConfigEnum.HB.getKey(), getScale(hb),
                    DecisionSupportConfigEnum.TB.getKey(), getScale(tb));
            result.setConclusionText(conclusionText);
        }

        // 设置月环比
        result.setMonthHbScale(hb);
        return result;
    }
    
    /**
     * 图表：景区客流月环比下降Top5
     *
     * @param satisfactionDownMax
     * @return
     */
    private List getCharData(List<RankingDto> satisfactionDownMax) {
        List<RankingDataDto> list = Lists.newArrayList();
        for (RankingDto v : satisfactionDownMax) {
            double scale = Math.abs(Double.parseDouble(v.getScale()));
            list.add(RankingDataDto.builder()
                    .name(v.getName())
                    .value(String.valueOf(scale))
                    .build());
        }
        return list;
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

        ScenicScreenQuery vo = new ScenicScreenQuery();
        vo.setBeginTime(startDate);
        vo.setEndTime(endDate);
        vo.setPageSize(500L);
        List<ScenicBaseVo> records = scenicService.queryPassengerFlowTop5(vo).getRecords();

        // 上上月
        vo.setBeginTime(starLastDate);
        vo.setEndTime(endLastDate);
        List<ScenicBaseVo> lastMonthRecords = scenicService.queryPassengerFlowTop5(vo).getRecords();


        TreeMap<Double, String> map = Maps.newTreeMap();

        // 游客流失最多景区名称
        for (ScenicBaseVo last : records) {
            for (ScenicBaseVo lastLast : lastMonthRecords) {
                // 找到同一 景区
                if (last.getName().equals(lastLast.getName())) {
                    int lastMonthValue = Integer.parseInt(last.getValue());
                    int lastLastMonthValue = Integer.parseInt(lastLast.getValue());
                    // 计算下降 的景区环比比
                    if (lastMonthValue < lastLastMonthValue) {
                        double scale = Double.parseDouble(computeScale(lastMonthValue, lastLastMonthValue));
                        map.put(scale, last.getValue());
                    }
                }
            }
        }
        List<RankingDto> result = Lists.newArrayList();
        map.forEach((key, value) -> {
            for (ScenicBaseVo lastMonth : records) {
                if (value.equals(lastMonth.getName())) {
                    result.add(RankingDto.builder().name(value).scale(key.toString()).id(lastMonth.getId()).value(lastMonth.getValue()).build());
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

}
