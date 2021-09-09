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
import com.yjtech.wisdom.tourism.extension.ExtensionExecutor;
import com.yjtech.wisdom.tourism.integration.extensionpoint.OneTravelExtensionConstant;
import com.yjtech.wisdom.tourism.integration.extensionpoint.OneTravelQryExtPt;
import com.yjtech.wisdom.tourism.integration.pojo.vo.FxDistQueryVO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 一码游订单量 -pass
 *
 * @author renguangqian
 * @date 2021/8/9 10:16
 */
@Component
public class OneTravelOrderNumberStrategyImpl extends BaseStrategy {

    @Resource
    private ExtensionExecutor extensionExecutor;

    /**
     * 一码游订单量
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
        fxDistQueryVO.setIsSimulation(isSimulation.byteValue());
        fxDistQueryVO.setBeginTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastMonthStr() + DecisionSupportConstants.START_DAY_STR));
        fxDistQueryVO.setEndTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastMonthStr() + DecisionSupportConstants.END_DAY_STR));
        String orderTotal = extensionExecutor.execute(OneTravelQryExtPt.class,
                buildBizScenario(OneTravelExtensionConstant.ONE_TRAVEL_QUANTITY, fxDistQueryVO.getIsSimulation()),
                extension -> extension.queryOneTravelTradeIndex(fxDistQueryVO)).getOrderCount().toString();


        fxDistQueryVO.setBeginTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentYearStr() + DecisionSupportConstants.START_DATE_STR));
        fxDistQueryVO.setEndTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentYearStr() + DecisionSupportConstants.END_DATE_STR));
        fxDistQueryVO.setType(DecisionSupportConstants.YEAR_MONTH);
        List<AnalysisBaseInfo> order = extensionExecutor.execute(OneTravelQryExtPt.class,
                buildBizScenario(OneTravelExtensionConstant.ONE_TRAVEL_QUANTITY, fxDistQueryVO.getIsSimulation()),
                extension -> extension.queryOrderAnalysis(fxDistQueryVO));

        String currentLastMonthStr1 = DateTimeUtil.getCurrentLastMonthStr();

        // 环比
        String hb = DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;
        // 同比
        String tb = DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;

        for (AnalysisBaseInfo item : order) {
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

        // 图表数据：订单趋势
        result.setChartData(order);

        // 处理指标报警
        switch (configId) {

            // 订单量_统计年月 （文本）
            case DecisionSupportConstants.DDL_TJNY :
                result.setWarnNum(currentLastMonthStr);
                textAlarmDeal(entity, result, currentLastMonthStr, isSimulation);
                break;

            // 订单量_一码游订单数量 （数值）
            case DecisionSupportConstants.DDL_YMLDDSL :
                result.setWarnNum(orderTotal);
                numberAlarmDeal(entity, result, hb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_NUMBER_VALUE.equals(Integer.parseInt(orderTotal))) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 订单量_环比变化（较上月） （数值）
            case DecisionSupportConstants.DDL_HBBH :
                result.setWarnNum(hb);
                numberAlarmDeal(entity, result, hb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(hb)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 订单量_同比变化（较去年同月） （数值）
            case DecisionSupportConstants.DDL_TBBH :
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
                    DecisionSupportConfigEnum.ONE_TRAVEL_ORDER_NUMBER.getKey(), orderTotal,
                    DecisionSupportConfigEnum.YEAR_MONTH_STATISTICAL.getKey(), currentLastMonthStr + DecisionSupportConstants.MONTH);
            result.setConclusionText(conclusionText);
        }

        // 设置月环比
        result.setMonthHbScale(hb);
        return result;
    }
}
