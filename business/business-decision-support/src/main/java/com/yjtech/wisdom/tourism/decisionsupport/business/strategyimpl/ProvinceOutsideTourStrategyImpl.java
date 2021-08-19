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
 * 省外游客 -pass
 *
 * @author renguangqian
 * @date 2021/8/5 20:10
 */
@Component
public class ProvinceOutsideTourStrategyImpl extends BaseStrategy {

    @Autowired
    private TargetQueryService targetQueryService;

    /**
     * 对象赋值方法
     *
     * @return
     */
    @Override
    public DecisionWarnEntity init(DecisionEntity entity, Integer isSimulation) {

        DecisionWarnEntity result = JSONObject.parseObject(JSONObject.toJSONString(entity), DecisionWarnEntity.class);

        int configId = entity.getConfigId().intValue();

        // 统计年月
        String currentLastMonthStr = super.getCurrentLastMonthStr();
        // 平台简称
        String simpleName = super.getPlatformSimpleName();
        // 省外游客数量
        String provinceOutsideNumber = targetQueryService.queryProvinceOutsideNumber(DecisionSupportConstants.PROVINCE_OUTSIDE_TYPE, isSimulation);
        // 环比
        String hb = targetQueryService.queryProvinceScale(TargetQueryConstants.PROVINCE_SCALE_HB, TargetQueryConstants.PROVINCE_OUTSIDE_TYPE, isSimulation);
        // 同比
        String tb = targetQueryService.queryProvinceScale(TargetQueryConstants.PROVINCE_SCALE_TB, TargetQueryConstants.PROVINCE_OUTSIDE_TYPE, isSimulation);

        // 处理指标报警
        switch (configId) {
            // 省外游客_省外游客数量 （数值），由于比较数量是环比，则直接使用环比进行指标处理
            case DecisionSupportConstants.SWYK_SWYKSL :
                result.setWarnNum(provinceOutsideNumber);
                numberAlarmDeal(entity, result, hb);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_NUMBER_VALUE.equals(Integer.valueOf(provinceOutsideNumber))) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 省外游客_环比变化（较上月） （数值）
            case DecisionSupportConstants.SWYK_HBBH :
                result.setWarnNum(hb);
                numberAlarmDeal(entity, result, hb);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(hb)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 省外游客_同比变化（较去年同月） （数值）
            case DecisionSupportConstants.SWYK_TBBH :
                result.setWarnNum(tb);
                numberAlarmDeal(entity, result, tb);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(tb)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 省外游客_统计年月 （文本）
            case DecisionSupportConstants.SWYK_TJNY :
                result.setWarnNum(currentLastMonthStr);
                textAlarmDeal(entity, result, currentLastMonthStr);
                break;

            // 省外游客_平台简称 （文本）
            case DecisionSupportConstants.SWYK_PTJC :
                result.setWarnNum(simpleName);
                textAlarmDeal(entity, result, simpleName);
                // 判断是否使用缺失话术
                if (StringUtils.isEmpty(simpleName)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 其余指标 不处理
            default:
                break;
        }

        // 处理话术
        String conclusionText = entity.getConclusionText();
        if (!StringUtils.isEmpty(conclusionText)) {
            conclusionText = PlaceholderUtils.replace(conclusionText,
                    DecisionSupportConfigEnum.HB.getKey(), getScale(hb),
                    DecisionSupportConfigEnum.TB.getKey(), getScale(tb),
                    DecisionSupportConfigEnum.PROVINCE_OUTSIDE_TOUR_NUM.getKey(), provinceOutsideNumber,
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
