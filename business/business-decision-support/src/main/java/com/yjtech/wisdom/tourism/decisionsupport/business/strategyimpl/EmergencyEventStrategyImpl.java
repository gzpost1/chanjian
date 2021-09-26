package com.yjtech.wisdom.tourism.decisionsupport.business.strategyimpl;

import com.alibaba.fastjson.JSONObject;
import com.yjtech.wisdom.tourism.command.query.event.EventSumaryQuery;
import com.yjtech.wisdom.tourism.command.service.screen.EmergencyEvenScreenService;
import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.enums.DecisionSupportConfigEnum;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.JsonUtils;
import com.yjtech.wisdom.tourism.decisionsupport.business.dto.OneTravelNumberDto;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnEntity;
import com.yjtech.wisdom.tourism.decisionsupport.common.strategy.BaseStrategy;
import com.yjtech.wisdom.tourism.decisionsupport.common.util.PlaceholderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 应急事件统计 -pass
 *
 * @author renguangqian
 * @date 2021/8/9 10:23
 */
@Component
public class EmergencyEventStrategyImpl extends BaseStrategy {

    @Autowired
    private EmergencyEvenScreenService emergencyEvenScreenService;

    /**
     * 应急事件统计
     *
     * @param entity
     * @return
     */
    @Override
    public DecisionWarnEntity init(DecisionEntity entity, Byte isSimulation) {
        DecisionWarnEntity result = JSONObject.parseObject(JSONObject.toJSONString(entity), DecisionWarnEntity.class);

        int configId = entity.getConfigId().intValue();

        // 统计年月
        String currentLastMonthStr = super.getCurrentLastMonthStr();

        // 应急事件数量
        String total = DecisionSupportConstants.ZERO;
        EventSumaryQuery eventSumaryQuery = new EventSumaryQuery();
        eventSumaryQuery.setIsSimulation(isSimulation);
        eventSumaryQuery.setBeginTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastMonthStr() + DecisionSupportConstants.START_DAY_STR));
        eventSumaryQuery.setEndTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastMonthStr() + DecisionSupportConstants.END_DAY_STR));
        List<BaseVO> totalList = emergencyEvenScreenService.queryEventQuantity(eventSumaryQuery);
        for (BaseVO v : totalList) {
            if (DecisionSupportConstants.TOTAL_STR.equals(v.getName())) {
                total = v.getValue();
            }
        }

        // 年趋势
        eventSumaryQuery.setBeginTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentYearStr() + DecisionSupportConstants.START_DATE_STR));
        eventSumaryQuery.setEndTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentYearStr() + DecisionSupportConstants.END_DATE_STR));
        eventSumaryQuery.setType(DecisionSupportConstants.YEAR_MONTH);
        List<BaseValueVO> baseValueList = emergencyEvenScreenService.querySaleTrend(eventSumaryQuery);

        String lastMonth = DateTimeUtil.getLastMonthStr();

        // 环比
        String hb = DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;
        // 同比
        String tb = DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;

        List<BaseValueVO> eventList = JSONObject.parseArray(JSONObject.toJSONString(baseValueList),  BaseValueVO.class);

        for (BaseValueVO bean : eventList) {
            // 获取年数据
            if (DecisionSupportConstants.THIS_YEAR.equals(bean.getName())) {
                List<?> value = bean.getValue();
                int i = Integer.parseInt(lastMonth);
                tb = String.valueOf(JsonUtils.getValueByKey(value.get(i), DecisionSupportConstants.YOY));
                hb = String.valueOf(JsonUtils.getValueByKey(value.get(i), DecisionSupportConstants.MOM));
            }
        }

        // 图表数据：应急事件趋势
        result.setChartData(baseValueList);

        // 处理指标报警
        switch (configId) {

            // 应急事件统计_统计年月 （文本）
            case DecisionSupportConstants.YJSJTJ_TJNY :
                result.setWarnNum(currentLastMonthStr);
                textAlarmDeal(entity, result, currentLastMonthStr, isSimulation);
                break;

            // 应急事件统计_应急事件数量 （数值）
            case DecisionSupportConstants.YJSJTJ_YJSJSL :
                result.setWarnNum(String.valueOf(total));
                numberAlarmDeal(entity, result, hb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_NUMBER_VALUE.equals(Integer.parseInt(total))) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 应急事件统计_环比变化（较上月） （数值）
            case DecisionSupportConstants.YJSJTJ_HBBH :
                result.setWarnNum(hb);
                numberAlarmDeal(entity, result, hb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(hb)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 应急事件统计_同比变化（较去年同月） （数值）
            case DecisionSupportConstants.YJSJTJ_TBBH :
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
                    DecisionSupportConfigEnum.EMERGENCIES_NUMBER.getKey(), total,
                    DecisionSupportConfigEnum.YEAR_MONTH_STATISTICAL.getKey(), currentLastMonthStr + DecisionSupportConstants.MONTH);
            result.setConclusionText(conclusionText);
        }

        // 设置月环比
        result.setMonthHbScale(hb);
        return result;
    }
}
