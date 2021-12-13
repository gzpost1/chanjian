package com.yjtech.wisdom.tourism.decisionsupport.business.strategyimpl;

import com.alibaba.fastjson.JSONObject;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.enums.DecisionSupportConfigEnum;
import com.yjtech.wisdom.tourism.decisionsupport.base.service.TargetQueryService;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnEntity;
import com.yjtech.wisdom.tourism.decisionsupport.common.constant.TargetQueryConstants;
import com.yjtech.wisdom.tourism.decisionsupport.common.strategy.BaseStrategy;
import com.yjtech.wisdom.tourism.decisionsupport.common.util.PlaceholderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 全部游客 -pass
 *
 * @author renguangqian
 * @date 2021/8/9 9:56
 */
@Component
public class ProvinceAllTourStrategyImpl extends BaseStrategy {

    @Autowired
    private TargetQueryService targetQueryService;

    /**
     * 全部游客
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
        // 平台简称
        String simpleName = super.getPlatformSimpleName();
        // 全部游客数量
        String provinceAllNumber = targetQueryService.queryProvinceNumber(DecisionSupportConstants.PROVINCE_ALL_TYPE, isSimulation).getAllTouristNum().toString();
        // 环比
        String hb = targetQueryService.queryProvinceScale(TargetQueryConstants.PROVINCE_SCALE_HB, DecisionSupportConstants.PROVINCE_ALL_TYPE, isSimulation);
        // 同比
        String tb = targetQueryService.queryProvinceScale(TargetQueryConstants.PROVINCE_SCALE_TB, DecisionSupportConstants.PROVINCE_ALL_TYPE, isSimulation);

        // 图标数据：月客流趋势
        result.setChartData(getProvinceCharData(DecisionSupportConstants.PROVINCE_ALL_TYPE, isSimulation));

        // 处理指标报警
        switch (configId) {

            // 整体游客 _统计年月 （文本）
            case DecisionSupportConstants.ZTYK_TJNY :
                result.setWarnNum(currentLastMonthStr);
                textAlarmDeal(entity, result, currentLastMonthStr, isSimulation);
                break;

            // 整体游客 _平台简称 （文本）
            case DecisionSupportConstants.ZTYK_PTJC :
                result.setWarnNum(simpleName);
                textAlarmDeal(entity, result, simpleName, isSimulation);
                // 判断是否使用缺失话术
                if (StringUtils.isEmpty(simpleName)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 整体游客 _整体游客数量 （数值）
            case DecisionSupportConstants.ZTYK_ZTYKSL :
                result.setWarnNum(provinceAllNumber);
                numberAlarmDeal(entity, result, hb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_NUMBER_VALUE.equals(Integer.valueOf(provinceAllNumber))) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 整体游客 _环比变化（较上月） （数值）
            case DecisionSupportConstants.ZTYK_HBBH :
                result.setWarnNum(hb);
                numberAlarmDeal(entity, result, hb, isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(hb)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 整体游客 _同比变化（较去年同月） （数值）
            case DecisionSupportConstants.ZTYK_TBBH :
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
                    DecisionSupportConfigEnum.PROVINCE_ALL_TOUR_NUM.getKey(), provinceAllNumber,
                    DecisionSupportConfigEnum.YEAR_MONTH_STATISTICAL.getKey(), currentLastMonthStr + DecisionSupportConstants.MONTH,
                    DecisionSupportConfigEnum.PLATFORM_SIMPLE_NAME.getKey(), simpleName
            );
            result.setConclusionText(conclusionText);
        }

        // 设置月环比
        result.setMonthHbScale(hb);
        return result;
    }
}
