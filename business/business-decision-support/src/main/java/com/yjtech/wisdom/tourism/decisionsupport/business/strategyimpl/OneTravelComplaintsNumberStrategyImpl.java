package com.yjtech.wisdom.tourism.decisionsupport.business.strategyimpl;

import com.alibaba.fastjson.JSONObject;
import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.enums.DecisionSupportConfigEnum;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.JsonUtils;
import com.yjtech.wisdom.tourism.decisionsupport.business.dto.OneTravelNumberDto;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnEntity;
import com.yjtech.wisdom.tourism.decisionsupport.common.strategy.BaseStrategy;
import com.yjtech.wisdom.tourism.decisionsupport.common.util.PlaceholderUtils;
import com.yjtech.wisdom.tourism.integration.pojo.vo.OneTravelQueryVO;
import com.yjtech.wisdom.tourism.integration.service.OneTravelApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 一码游投诉量 -pass
 *
 * @author renguangqian
 * @date 2021/8/9 10:15
 */
@Component
public class OneTravelComplaintsNumberStrategyImpl extends BaseStrategy {


    @Autowired
    private OneTravelApiService oneTravelApiService;

    /**
     * 一码游投诉量
     *
     * @param entity
     * @return
     */
    @Override
    public DecisionWarnEntity init(DecisionEntity entity, Integer isSimulation) {
        DecisionWarnEntity result = JSONObject.parseObject(JSONObject.toJSONString(entity), DecisionWarnEntity.class);

        int configId = entity.getConfigId().intValue();

        // 统计年月
        String currentLastMonthStr = super.getCurrentLastMonthStr();

        OneTravelQueryVO oneTravelQueryVO = new OneTravelQueryVO();
        oneTravelQueryVO.setBeginTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastMonthStr() + DecisionSupportConstants.START_DAY_STR));
        oneTravelQueryVO.setEndTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastMonthStr() + DecisionSupportConstants.END_DAY_STR));
        oneTravelQueryVO.setType(DecisionSupportConstants.YEAR_MONTH_DAY);
        List<AnalysisBaseInfo> analysisBaseInfos = oneTravelApiService.queryComplaintAnalysis(oneTravelQueryVO);

        String currentLastMonthStr1 = DateTimeUtil.getCurrentLastMonthStr();

        Integer total = 0;

        // 环比
        String hb = DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;
        // 同比
        String tb = DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;

        for (AnalysisBaseInfo item : analysisBaseInfos) {
            List<OneTravelNumberDto> oneTravelComplaintsNumberDtos = JSONObject.parseArray(
                    JSONObject.toJSONString(JsonUtils.getValueByKey(JSONObject.toJSONString(item), DecisionSupportConstants.DATA)),
                    OneTravelNumberDto.class);
            for (OneTravelNumberDto v : oneTravelComplaintsNumberDtos) {
                if (currentLastMonthStr1.equals(v.getTime())) {
                    if (!StringUtils.isEmpty(v.getHb())) {
                        hb = v.getHb();
                    }
                    if (!StringUtils.isEmpty(v.getTb())) {
                        tb = v.getTb();
                    }
                    if (!ObjectUtils.isEmpty(v.getCount())) {
                        total = v.getCount();
                    }
                }
            }
        }

        // 图表数据：投诉趋势
        result.setChartData(analysisBaseInfos);

        // 处理指标报警
        switch (configId) {

            // 投诉量_统计年月 （文本）
            case DecisionSupportConstants.TSL_TJNY :
                result.setWarnNum(currentLastMonthStr);
                textAlarmDeal(entity, result, currentLastMonthStr, isSimulation);
                break;

            // 投诉量_一码游投诉数量 （数值）
            case DecisionSupportConstants.TSL_YMYTSL :
                result.setWarnNum(String.valueOf(total));
                numberAlarmDeal(entity, result, hb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_NUMBER_VALUE.equals(total)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 投诉量_环比变化（较上月） （数值）
            case DecisionSupportConstants.TSL_HBBH :
                result.setWarnNum(hb);
                numberAlarmDeal(entity, result, hb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(hb)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 投诉量_同比变化（较去年同月） （数值）
            case DecisionSupportConstants.TSL_TBBH :
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
                    DecisionSupportConfigEnum.HB.getKey(), getScale(hb),
                    DecisionSupportConfigEnum.TB.getKey(), getScale(tb),
                    DecisionSupportConfigEnum.ONE_TRAVEL_COMPLAINTS_NUMBER.getKey(), String.valueOf(total),
                    DecisionSupportConfigEnum.YEAR_MONTH_STATISTICAL.getKey(), currentLastMonthStr + DecisionSupportConstants.MONTH);
            result.setConclusionText(conclusionText);
        }

        // 设置月环比
        result.setMonthHbScale(hb);
        return result;
    }
}
