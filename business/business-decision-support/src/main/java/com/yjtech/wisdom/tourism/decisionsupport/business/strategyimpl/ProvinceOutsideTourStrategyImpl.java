package com.yjtech.wisdom.tourism.decisionsupport.business.strategyimpl;

import com.alibaba.fastjson.JSONObject;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.enums.DecisionSupportConfigEnum;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
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
 * 省外游客
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
    public DecisionWarnEntity init(DecisionEntity decisionInfo) {

        DecisionWarnEntity entity = JSONObject.parseObject(JSONObject.toJSONString(decisionInfo), DecisionWarnEntity.class);

        int configId = decisionInfo.getConfigId().intValue();

        // 统计年月
        String currentLastMonthStr = DateTimeUtil.getCurrentLastMonthStr();
        // 平台简称
        String simpleName = targetQueryService.queryPlatformSimpleName().getSimpleName();
        // 省外游客数量
        String provinceOutsideNumber = targetQueryService.queryProvinceOutsideNumber(DecisionSupportConstants.PROVINCE_OUTSIDE_TYPE);
        // 环比
        String hb = targetQueryService.queryProvinceOutsideScale(TargetQueryConstants.PROVINCE_OUTSIDE_SCALE_HB);
        // 同比
        String tb = targetQueryService.queryProvinceOutsideScale(TargetQueryConstants.PROVINCE_OUTSIDE_SCALE_TB);

        // 处理指标报警
        switch (configId) {
            // 省外游客_省外游客数量 （数值），由于比较数量是环比，则直接使用环比进行指标处理
            case DecisionSupportConstants.SWYK_SWYKSL :
                entity.setWarnNum(hb);
                numberAlarmDeal(decisionInfo, entity, hb);
                break;

            // 省外游客_环比变化（较上月） （数值）
            case DecisionSupportConstants.SWYK_HBBH :
                entity.setWarnNum(hb);
                numberAlarmDeal(decisionInfo, entity, hb);
                break;

            // 省外游客_同比变化（较去年同月） （数值）
            case DecisionSupportConstants.SWYK_TBBH :
                entity.setWarnNum(tb);
                numberAlarmDeal(decisionInfo, entity, tb);
                break;

            // 省外游客_统计年月 （文本）
            case DecisionSupportConstants.SWYK_TJNY :
                entity.setWarnNum(currentLastMonthStr);
                textAlarmDeal(decisionInfo, entity, currentLastMonthStr);
                break;

            // 省外游客_平台简称 （文本）
            case DecisionSupportConstants.SWYK_PTJC :
                entity.setWarnNum(simpleName);
                textAlarmDeal(decisionInfo, entity, simpleName);
                break;

            // 其余指标 不处理
            default:
                break;
        }

        // 处理话术
        String conclusionText = decisionInfo.getConclusionText();
        if (!StringUtils.isEmpty(conclusionText)) {
            conclusionText = PlaceholderUtils.replace(conclusionText,
                    DecisionSupportConfigEnum.PROVINCE_OUTSIDE_TOUR_HB.getKey(), hb + "%",
                    DecisionSupportConfigEnum.PROVINCE_OUTSIDE_TOUR_TB.getKey(), tb + "%",
                    DecisionSupportConfigEnum.PROVINCE_OUTSIDE_TOUR_NUM.getKey(), provinceOutsideNumber,
                    DecisionSupportConfigEnum.YEAR_MONTH_STATISTICAL.getKey(), currentLastMonthStr + "月",
                    DecisionSupportConfigEnum.PLATFORM_SIMPLE_NAME.getKey(), simpleName
            );
            entity.setConclusionText(conclusionText);
        }

        // 设置月环比
        entity.setMonthHbScale(hb);
        return entity;
    }
}
