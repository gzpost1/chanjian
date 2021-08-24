package com.yjtech.wisdom.tourism.decisionsupport.business.strategyimpl;

import com.alibaba.fastjson.JSONObject;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.enums.DecisionSupportConfigEnum;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnEntity;
import com.yjtech.wisdom.tourism.decisionsupport.common.strategy.BaseStrategy;
import com.yjtech.wisdom.tourism.decisionsupport.common.util.PlaceholderUtils;
import com.yjtech.wisdom.tourism.dto.MonthPassengerFlowDto;
import com.yjtech.wisdom.tourism.mybatis.utils.AnalysisUtils;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicScreenQuery;
import com.yjtech.wisdom.tourism.resource.scenic.service.ScenicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 整体景区客流 -pass
 *
 * @author renguangqian
 * @date 2021/8/9 10:00
 */
@Component
public class OverallScenicSpotsTouristFlowStrategyImpl extends BaseStrategy {

    @Autowired
    private ScenicService scenicService;

    /**
     * 整体景区客流
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

        // 平台简称
        String platformSimpleName = super.getPlatformSimpleName();

        // 客流趋势
        ScenicScreenQuery query = new ScenicScreenQuery();
        query.setBeginTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentYearStr() + DecisionSupportConstants.START_DATE_STR));
        query.setEndTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentYearStr() + DecisionSupportConstants.END_DATE_STR));
        query.setType(DecisionSupportConstants.YEAR_MONTH);
        List<MonthPassengerFlowDto> monthPassengerFlowDtos = scenicService.queryPassengerFlowTrend(query);

        Integer total = DecisionSupportConstants.ZERO_NUMBER;
        String tb = DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;
        String hb = DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;

        // 上月日期
        String currentLastMonth = DateTimeUtil.getCurrentLastMonthStr();
        for (MonthPassengerFlowDto v : monthPassengerFlowDtos) {
            if (currentLastMonth.equals(v.getDate())) {
                total = v.getNumber();
                tb = v.getTbScale();
                hb = v.getHbScale();
            }
        }

        // 图表数据：月景区客流趋势
        result.setChartData(getCharData(query));

        // 处理指标报警
        switch (configId) {

            // 整体景区客流 _统计年月 （文本）
            case DecisionSupportConstants.ZTJQKL_TJNY :
                result.setWarnNum(currentLastMonthStr);
                textAlarmDeal(entity, result, currentLastMonthStr, isSimulation);
                break;

            // 整体景区客流 _平台简称 （文本）
            case DecisionSupportConstants.ZTJQKL_PTJC :
                result.setWarnNum(platformSimpleName);
                textAlarmDeal(entity, result, platformSimpleName, isSimulation);
                // 判断是否使用缺失话术
                if (StringUtils.isEmpty(platformSimpleName)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 整体景区客流 _全部景区接待数量 （数值）
            case DecisionSupportConstants.ZTJQKL_QBJQJDSL :
                result.setWarnNum(String.valueOf(total));
                numberAlarmDeal(entity, result, hb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_NUMBER_VALUE.equals(total)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 整体景区客流 _环比变化（较上月） （数值）
            case DecisionSupportConstants.ZTJQKL_HBBH :
                result.setWarnNum(hb);
                numberAlarmDeal(entity, result, hb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(hb)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 整体景区客流 _同比变化（较去年同月） （数值）
            case DecisionSupportConstants.ZTJQKL_TBBH :
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
                    DecisionSupportConfigEnum.PLATFORM_SIMPLE_NAME.getKey(), platformSimpleName,
                    DecisionSupportConfigEnum.ALL_SCENIC_SPOTS_RECEPTIONS_NUMBER.getKey(), String.valueOf(total),
                    DecisionSupportConfigEnum.HB.getKey(), getScale(hb),
                    DecisionSupportConfigEnum.TB.getKey(), getScale(tb));
            result.setConclusionText(conclusionText);
        }

        // 设置月环比
        result.setMonthHbScale(hb);
        return result;
    }

    /**
     * 图表：月景区客流
     *
     * @param query
     * @return
     */
    private List<BaseValueVO> getCharData(ScenicScreenQuery query) {
        // 当年 上月
        LocalDateTime startDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentYearStr() + DecisionSupportConstants.START_DATE_STR);
        LocalDateTime endDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentYearStr() + DecisionSupportConstants.END_DATE_STR);
        query.setBeginTime(startDate);
        query.setEndTime(endDate);

        query.setType(DecisionSupportConstants.YEAR_MONTH);

        return AnalysisUtils.MultipleBuildAnalysis(
                query,
                scenicService.queryPassengerFlowTrend(query),
                true,
                MonthPassengerFlowDto::getNumber, MonthPassengerFlowDto::getTbNumber, MonthPassengerFlowDto::getHbScale, MonthPassengerFlowDto::getTbScale);

    }
}
