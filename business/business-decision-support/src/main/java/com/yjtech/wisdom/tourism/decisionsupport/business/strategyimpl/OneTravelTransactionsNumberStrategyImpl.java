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
import com.yjtech.wisdom.tourism.integration.pojo.vo.FxDistQueryVO;
import com.yjtech.wisdom.tourism.integration.service.FxDistApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 一码游交易额 -pass
 *
 * @author renguangqian
 * @date 2021/8/9 10:18
 */
@Component
public class OneTravelTransactionsNumberStrategyImpl extends BaseStrategy {

    @Autowired
    private FxDistApiService fxDistApiService;

    /**
     * 一码游交易额
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

        // 一码游订单数量
        FxDistQueryVO fxDistQueryVO = new FxDistQueryVO();
        fxDistQueryVO.setBeginTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastMonthStr() + DecisionSupportConstants.START_DAY_STR));
        fxDistQueryVO.setEndTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastMonthStr() + DecisionSupportConstants.END_DAY_STR));
        String orderSalesTotal = fxDistApiService.queryOrderStatistics(fxDistQueryVO).getOrderSum().toString();

        fxDistQueryVO.setBeginTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentYearStr() + DecisionSupportConstants.START_DATE_STR));
        fxDistQueryVO.setEndTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentYearStr() + DecisionSupportConstants.END_DATE_STR));
        fxDistQueryVO.setType(DecisionSupportConstants.YEAR_MONTH);
        List<AnalysisBaseInfo> sales = fxDistApiService.queryOrderSumAnalysis(fxDistQueryVO);

        String currentLastMonthStr1 = DateTimeUtil.getCurrentLastMonthStr();

        // 环比
        String hb = DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;
        // 同比
        String tb = DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;

        for (AnalysisBaseInfo item : sales) {
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
                }
            }
        }

        // 图表：一码游交易额年趋势
        result.setChartData(sales);

        // 处理指标报警
        switch (configId) {

            // 交易额_统计年月 （文本）
            case DecisionSupportConstants.JYE_TJNY :
                result.setWarnNum(currentLastMonthStr);
                textAlarmDeal(entity, result, currentLastMonthStr, isSimulation);
                break;

            // 交易额_一码游交易额 （数值）
            case DecisionSupportConstants.JYE_YMYJYE :
                result.setWarnNum(orderSalesTotal);
                numberAlarmDeal(entity, result, hb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_NUMBER_VALUE.equals(Integer.parseInt(orderSalesTotal))) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 交易额_环比变化（较上月） （数值）
            case DecisionSupportConstants.JYE_HBBH :
                result.setWarnNum(hb);
                numberAlarmDeal(entity, result, hb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(hb)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 交易额_同比变化（较去年同月） （数值）
            case DecisionSupportConstants.JYE_TBBH :
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
                    DecisionSupportConfigEnum.ONE_TRAVEL_TRANSACTION_AMOUNT.getKey(), String.valueOf(orderSalesTotal),
                    DecisionSupportConfigEnum.YEAR_MONTH_STATISTICAL.getKey(), currentLastMonthStr + DecisionSupportConstants.MONTH);
            result.setConclusionText(conclusionText);
        }

        // 设置月环比
        result.setMonthHbScale(hb);
        return result;
    }
}
