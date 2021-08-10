package com.yjtech.wisdom.tourism.decisionsupport.business.strategyimpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.enums.DecisionSupportConfigEnum;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.decisionsupport.base.service.TargetQueryService;
import com.yjtech.wisdom.tourism.decisionsupport.business.dto.ComprehensiveAlarmDataDto;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.mapper.DecisionWarnMapper;
import com.yjtech.wisdom.tourism.decisionsupport.common.strategy.BaseStrategy;
import com.yjtech.wisdom.tourism.decisionsupport.common.util.PlaceholderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 综合概况
 *
 * @author renguangqian
 * @date 2021/8/7 10:48
 */
@Component
public class ComprehensiveStrategyImpl extends BaseStrategy {

    @Autowired
    private TargetQueryService targetQueryService;

    @Autowired
    private DecisionWarnMapper decisionWarnMapper;

    /**
     * 综合概况
     *
     * @param list
     * @param entity
     * @return
     */
    @Override
    public DecisionWarnEntity init(List<DecisionWarnEntity> list, DecisionEntity entity) {

        DecisionWarnEntity result = JSONObject.parseObject(JSONObject.toJSONString(entity), DecisionWarnEntity.class);

        // 本月报警数据
        ComprehensiveAlarmDataDto currentAlarmData = this.computeAlarmData(list);

        ComprehensiveAlarmDataDto lastMonthAlarmData = null;

        // 统计年月
        String currentLastMonthStr = DateTimeUtil.getCurrentLastMonthStr();
        // 平台简称
        String simpleName = targetQueryService.queryPlatformSimpleName().getSimpleName();

        // 如果是数值类型 则查询上月生成的综合概况统计数据
        if (DecisionSupportConstants.DECISION_WARN_TYPE_NUMBER.equals(entity.getConfigType())) {
            List<DecisionWarnEntity> LastMonthDecisionWarnEntity = decisionWarnMapper.selectList(
                    new LambdaQueryWrapper<DecisionWarnEntity>()
                            .between(DecisionWarnEntity::getCreateTime,
                                    DateTimeUtil.getCurrentLastMonthFirstDayStr() + " 00:00:00",
                                    DateTimeUtil.getCurrentLastMonthLastDayStr() + " 23:59:59"
                            )
            );

            if (!CollectionUtils.isEmpty(LastMonthDecisionWarnEntity)) {
                // 计算上月统计数量
                lastMonthAlarmData = this.computeAlarmData(LastMonthDecisionWarnEntity);
            }
        }

        switch (entity.getConfigId().intValue()) {
            // 综合概况_统计年月 （文本）
            case DecisionSupportConstants.ZHGK_TJNY:
                result.setWarnNum(currentLastMonthStr);
                textAlarmDeal(entity, result, currentLastMonthStr);
                break;

            // 综合概况_平台名称 （文本）
            case DecisionSupportConstants.ZHGK_PTMC:
                result.setWarnNum(simpleName);
                textAlarmDeal(entity, result, simpleName);
                break;

            // 综合概况_高风险项指标项 （文本）
            case DecisionSupportConstants.ZHGK_GFXXZBX:
                String highName = currentAlarmData.getHighRiskName();
                result.setWarnNum(highName);
                textAlarmDeal(entity, result, highName);
                break;

            // 综合概况_中风险项指标项 （文本）
            case DecisionSupportConstants.ZHGK_ZFXXZBX:
                String mediumName = currentAlarmData.getMediumRiskName();
                result.setWarnNum(mediumName);
                textAlarmDeal(entity, result, mediumName);
                break;

            // 综合概况_低风险项指标项 （文本）
            case DecisionSupportConstants.ZHGK_XFXXZBX:
                String lowName = currentAlarmData.getLowRiskName();
                result.setWarnNum(lowName);
                textAlarmDeal(entity, result, lowName);
                break;


            // 综合概况_风险预警项总数量 （数值）
            case DecisionSupportConstants.ZHGK_FXYJXSL :
                // 进行对象非空判断
                if (!ObjectUtils.isEmpty(currentAlarmData)){
                    Integer totalRiskNumber = 0;
                    if (!ObjectUtils.isEmpty(lastMonthAlarmData)) {
                        totalRiskNumber = lastMonthAlarmData.getTotalRiskNumber();
                    }

                    // 计算比例
                    String scale = computeScale(currentAlarmData.getTotalRiskNumber(), totalRiskNumber);
                    // 数值报警处理
                    numberAlarmDeal(entity, result, scale);
                    result.setMonthHbScale(scale);
                }
                // 设置预警值
                result.setWarnNum(String.valueOf(currentAlarmData.getTotalRiskNumber()));
                break;

            // 综合概况_高风险项数量 （数值）
            case DecisionSupportConstants.ZHGK_GFXXSL :
                // 进行对象非空判断
                if (!ObjectUtils.isEmpty(currentAlarmData)){
                    Integer highRiskNumber = 0;
                    if (!ObjectUtils.isEmpty(lastMonthAlarmData)) {
                        highRiskNumber = lastMonthAlarmData.getHighRiskNumber();
                    }

                    // 计算比例
                    String scale = computeScale(currentAlarmData.getHighRiskNumber(), highRiskNumber);
                    // 数值报警处理
                    numberAlarmDeal(entity, result, scale);
                    result.setMonthHbScale(scale);
                }
                // 设置预警值
                result.setWarnNum(String.valueOf(currentAlarmData.getHighRiskNumber()));
                break;

            // 综合概况_中风险项数量 （数值）
            case DecisionSupportConstants.ZHGK_ZFXXSL :
                // 进行对象非空判断
                if (!ObjectUtils.isEmpty(currentAlarmData)){
                    Integer mediumRiskNumber = 0;
                    if (!ObjectUtils.isEmpty(lastMonthAlarmData)) {
                        mediumRiskNumber = lastMonthAlarmData.getMediumRiskNumber();
                    }

                    // 计算比例
                    String scale = computeScale(currentAlarmData.getMediumRiskNumber(), mediumRiskNumber);
                    // 数值报警处理
                    numberAlarmDeal(entity, result, scale);
                    result.setMonthHbScale(scale);
                }
                // 设置预警值
                result.setWarnNum(String.valueOf(currentAlarmData.getMediumRiskNumber()));
                break;

            // 综合概况_低风险项数量 （数值）
            case DecisionSupportConstants.ZHGK_DFXXSL :
                // 进行对象非空判断
                if (!ObjectUtils.isEmpty(currentAlarmData)
                        && !ObjectUtils.isEmpty(lastMonthAlarmData)){
                    Integer lowRiskNumber = 0;
                    if (!ObjectUtils.isEmpty(lastMonthAlarmData)) {
                        lowRiskNumber = lastMonthAlarmData.getLowRiskNumber();
                    }

                    // 计算比例
                    String scale = computeScale(currentAlarmData.getLowRiskNumber(), lowRiskNumber);
                    // 数值报警处理
                    numberAlarmDeal(entity, result, scale);
                    result.setMonthHbScale(scale);
                }
                // 设置预警值
                result.setWarnNum(String.valueOf(currentAlarmData.getLowRiskNumber()));
                break;

            default:
                break;
        }

        // 处理话术
        dealConclusionText(entity.getConclusionText(), result, currentAlarmData, currentLastMonthStr, simpleName);

        // 设置预警名称
        result.setWarnName(entity.getConfigName());

        return result;
    }

    /**
     * 处理话术
     *
     * @param conclusionText
     * @param result
     * @param currentAlarmData
     * @param currentLastMonthStr
     * @param simpleName
     */
    private void dealConclusionText(String conclusionText, DecisionWarnEntity result, ComprehensiveAlarmDataDto currentAlarmData, String currentLastMonthStr, String simpleName) {
        // 处理话术
        if (!StringUtils.isEmpty(conclusionText)) {
            conclusionText = PlaceholderUtils.replace(conclusionText,
                    DecisionSupportConfigEnum.RISK_WARNING_ITEMS_NUMBER.getKey(), String.valueOf(currentAlarmData.getTotalRiskNumber()),
                    DecisionSupportConfigEnum.HIGH_RISK_WARNING_ITEMS_NUMBER.getKey(),String.valueOf(currentAlarmData.getHighRiskNumber()),
                    DecisionSupportConfigEnum.MIDDLE_RISK_WARNING_ITEMS_NUMBER.getKey(), String.valueOf(currentAlarmData.getMediumRiskNumber()),
                    DecisionSupportConfigEnum.LOW_RISK_WARNING_ITEMS_NUMBER.getKey(), String.valueOf(currentAlarmData.getLowRiskNumber()),
                    DecisionSupportConfigEnum.HIGH_RISK_WARNING_ITEMS_TARGET.getKey(), currentAlarmData.getHighRiskName(),
                    DecisionSupportConfigEnum.MIDDLE_RISK_WARNING_ITEMS_TARGET.getKey(), currentAlarmData.getMediumRiskName(),
                    DecisionSupportConfigEnum.LOW_RISK_WARNING_ITEMS_TARGET.getKey(), currentAlarmData.getLowRiskName(),
                    DecisionSupportConfigEnum.YEAR_MONTH_STATISTICAL.getKey(), currentLastMonthStr + "月",
                    DecisionSupportConfigEnum.PLATFORM_SIMPLE_NAME.getKey(), simpleName
            );
            result.setConclusionText(conclusionText);
        }
    }

    /**
     * 计算报警数据
     *
     * @param list
     * @return
     */
    private ComprehensiveAlarmDataDto computeAlarmData (List<DecisionWarnEntity> list) {
        // 低风险数量
        Integer lowRiskNumber = 0;
        // 中风险数量
        Integer mediumRiskNumber = 0;
        // 高风险数量
        Integer highRiskNumber = 0;

        // 低风险名称
        List<String> lowRiskName = Lists.newArrayList();
        // 中风险名称
        List<String> mediumRiskName = Lists.newArrayList();
        // 高风险名称
        List<String> highRiskName = Lists.newArrayList();

        for (DecisionWarnEntity item : list) {
            // 计算低风险数量、名称
            if (Integer.valueOf(DecisionSupportConstants.LOW_RISK_TYPE).equals(item.getAlarmType())) {
                lowRiskNumber += 1;
                lowRiskName.add(item.getTargetName());
            }
            // 计算中风险数量、名称
            else if (Integer.valueOf(DecisionSupportConstants.MEDIUM_RISK_TYPE).equals(item.getAlarmType())) {
                mediumRiskNumber += 1;
                mediumRiskName.add(item.getTargetName());
            }
            // 计算高风险数量、名称
            else if (Integer.valueOf(DecisionSupportConstants.HIGH_RISK_TYPE).equals(item.getAlarmType())) {
                highRiskNumber += 1;
                highRiskName.add(item.getTargetName());
            }
        }

        // 风险预警项总数
        Integer totalRiskNumber = lowRiskNumber + mediumRiskNumber + highRiskNumber;

        return ComprehensiveAlarmDataDto.builder()
                .lowRiskNumber(lowRiskNumber)
                .mediumRiskNumber(mediumRiskNumber)
                .highRiskNumber(highRiskNumber)
                .lowRiskName(conversionListToAlarmText(lowRiskName))
                .mediumRiskName(conversionListToAlarmText(mediumRiskName))
                .highRiskName(conversionListToAlarmText(highRiskName))
                .totalRiskNumber(totalRiskNumber)
                .build();
    }

    /**
     * list转换 String "、"分隔
     *
     * @param alarmTextList
     * @return
     */
    private String conversionListToAlarmText (List<String> alarmTextList) {
        String text = "";
        for (int i = 0; i < alarmTextList.size(); i++) {
            String sign = "、";
            if (i == alarmTextList.size() -1) {
                sign = "";
            }
            text += alarmTextList.get(i) + sign;
        }
        return text;
    }
}