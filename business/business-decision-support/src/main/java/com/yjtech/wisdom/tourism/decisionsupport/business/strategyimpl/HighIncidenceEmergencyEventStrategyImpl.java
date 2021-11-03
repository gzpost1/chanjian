package com.yjtech.wisdom.tourism.decisionsupport.business.strategyimpl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.command.query.event.EventSumaryQuery;
import com.yjtech.wisdom.tourism.command.service.screen.EmergencyEvenScreenService;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.enums.DecisionSupportConfigEnum;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.decisionsupport.business.dto.HighIncidenceEventTypeDto;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnEntity;
import com.yjtech.wisdom.tourism.decisionsupport.common.strategy.BaseStrategy;
import com.yjtech.wisdom.tourism.decisionsupport.common.util.PlaceholderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 高发应急事件 -pass
 *
 * @author renguangqian
 * @date 2021/8/9 10:25
 */
@Component
public class HighIncidenceEmergencyEventStrategyImpl extends BaseStrategy {

    @Autowired
    private EmergencyEvenScreenService emergencyEvenScreenService;

    /**
     * 高发应急事件
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
        //eventSumaryQuery.setType();
        eventSumaryQuery.setIsSimulation(isSimulation);
        List<BaseVO> totalList = Lists.newArrayList();
        try {
            totalList = emergencyEvenScreenService.queryEventQuantity(eventSumaryQuery);
        }catch (Exception e) {
            e.printStackTrace();
        }

        for (BaseVO v : totalList) {
            if (DecisionSupportConstants.TOTAL_STR.equals(v.getName())) {
                total = v.getValue();
            }
        }

        // 当年 上月
        LocalDateTime startDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastMonthStr() + DecisionSupportConstants.START_DAY_STR);
        LocalDateTime endDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastMonthStr() + DecisionSupportConstants.END_DAY_STR);
        // 当年 上上月
        LocalDateTime starLastDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastLastMonthStr() + DecisionSupportConstants.START_DAY_STR);
        LocalDateTime endLastDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentLastLastMonthStr() + DecisionSupportConstants.END_DAY_STR);
        // 去年 上月
        LocalDateTime startLastYearDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getLastYearLastMonthStr() + DecisionSupportConstants.START_DAY_STR);
        LocalDateTime endLastYearDate = DateTimeUtil.getLocalDateTime(DateTimeUtil.getLastYearLastMonthStr() + DecisionSupportConstants.END_DAY_STR);

        // 今年上月
        EventSumaryQuery lastMonth = new EventSumaryQuery();
        lastMonth.setIsSimulation(isSimulation);
        lastMonth.setBeginTime(startDate);
        lastMonth.setEndTime(endDate);
        lastMonth.setType(DecisionSupportConstants.YEAR_MONTH);

        // 今年上上月
        EventSumaryQuery lastLastMonth = new EventSumaryQuery();
        lastLastMonth.setIsSimulation(isSimulation);
        lastLastMonth.setBeginTime(starLastDate);
        lastLastMonth.setType(DecisionSupportConstants.YEAR_MONTH);
        lastLastMonth.setEndTime(endLastDate);

        // 去年上月
        EventSumaryQuery lastYearLastMonth = new EventSumaryQuery();
        lastYearLastMonth.setIsSimulation(isSimulation);
        lastLastMonth.setType(DecisionSupportConstants.YEAR_MONTH);
        lastYearLastMonth.setBeginTime(startLastYearDate);
        lastYearLastMonth.setEndTime(endLastYearDate);


        HighIncidenceEventTypeDto maxEventType = new HighIncidenceEventTypeDto();
        HighIncidenceEventTypeDto maxEventLevel = new HighIncidenceEventTypeDto();
        try {
            List<BaseVO> lastMonthTypeData = emergencyEvenScreenService.queryEventType(lastMonth);
            List<BaseVO> lastLastMonthTypeData = emergencyEvenScreenService.queryEventType(lastLastMonth);
            List<BaseVO> lastYearLastMonthTypeData = emergencyEvenScreenService.queryEventType(lastYearLastMonth);

            // 高发事件类型
            maxEventType = findMaxObject(lastMonthTypeData, lastLastMonthTypeData, lastYearLastMonthTypeData);


            // 高发事件等级
            List<BaseVO> lastMonthLevelData = emergencyEvenScreenService.queryEventLevel(lastMonth);
            List<BaseVO> lastLastMonthLevelData = emergencyEvenScreenService.queryEventLevel(lastLastMonth);
            List<BaseVO> lastYearLastMonthLevelData = emergencyEvenScreenService.queryEventLevel(lastYearLastMonth);

            // 高发事件等级
            maxEventLevel = findMaxObject(lastMonthLevelData, lastLastMonthLevelData, lastYearLastMonthLevelData);


        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 图表数据：
            result.setChartData(getCharData(maxEventType, maxEventLevel));
        }

        // 处理指标报警
        switch (configId) {

            // 高并发应急事件_统计年月 （文本）
            case DecisionSupportConstants.GBFYJSJ_TJNY :
                result.setWarnNum(currentLastMonthStr);
                textAlarmDeal(entity, result, currentLastMonthStr, isSimulation);
                break;

            // 高并发应急事件_高发事件类型 （文本）
            case DecisionSupportConstants.GBFYJSJ_GBFSJLX :
                String typeName = StringUtils.isEmpty(maxEventType.getName()) ? "-" : maxEventType.getName();
                result.setWarnNum(typeName);
                textAlarmDeal(entity, result, typeName, isSimulation);
                // 判断是否使用缺失话术
                if (StringUtils.isEmpty(typeName)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 高并发应急事件_高发事件等级 （文本）
            case DecisionSupportConstants.GBFYJSJ_GBFSJDJ :
                String levelName = StringUtils.isEmpty(maxEventLevel.getName()) ? "-" : maxEventLevel.getName();
                result.setWarnNum(levelName);
                textAlarmDeal(entity, result, levelName, isSimulation);
                // 判断是否使用缺失话术
                if (StringUtils.isEmpty(levelName)) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 高并发应急事件_应急事件数量 （数值）
            case DecisionSupportConstants.GBFYJSJ_YJSJSL :
                result.setWarnNum(maxEventType.getValue());
                numberAlarmDeal(entity, result, maxEventType.getHb(), isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_NUMBER_VALUE.equals(Integer.parseInt(total))) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 高并发应急事件_高发事件类型环比变化（较上月） （数值）
            case DecisionSupportConstants.GBFYJSJ_GBFSJLXHBBH :
                result.setWarnNum(maxEventType.getHb());
                numberAlarmDeal(entity, result, maxEventType.getHb(), isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(maxEventType.getHb())) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 高并发应急事件_高发事件类型同比变化（较去年同月） （数值）
            case DecisionSupportConstants.GBFYJSJ_GBFSJLXTBBH :
                result.setWarnNum(maxEventType.getTb());
                numberAlarmDeal(entity, result, maxEventType.getTb(), isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(maxEventType.getTb())) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 高并发应急事件_高发事件等级环比变化（较上月） （数值）
            case DecisionSupportConstants.GBFYJSJ_GBFSJDJHBBH :
                result.setWarnNum(maxEventLevel.getHb());
                numberAlarmDeal(entity, result, maxEventLevel.getHb(), isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(maxEventLevel.getHb())) {
                    result.setIsUseMissConclusionText(DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT);
                }
                break;

            // 高并发应急事件_高发事件等级同比变化（较去年同月） （数值）
            case DecisionSupportConstants.GBFYJSJ_GBFSJDJTBBH :
                result.setWarnNum(maxEventLevel.getTb());
                numberAlarmDeal(entity, result, maxEventLevel.getTb(), isSimulation);
                // 判断是否使用缺失话术
                if (DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(maxEventLevel.getTb())) {
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
                    DecisionSupportConfigEnum.HIGH_INCIDENCE_EMERGENCIES_TYPE.getKey(), StringUtils.isEmpty(maxEventType.getName()) ? "" : maxEventType.getName(),
                    DecisionSupportConfigEnum.HIGH_INCIDENCE_EMERGENCIES_TYPE_HB.getKey(), getScale(maxEventType.getHb()),
                    DecisionSupportConfigEnum.HIGH_INCIDENCE_EMERGENCIES_TYPE_TB.getKey(), getScale(maxEventType.getTb()),

                    DecisionSupportConfigEnum.HIGH_INCIDENCE_EVENT_GRADE.getKey(), StringUtils.isEmpty(maxEventLevel.getName()) ? "" : maxEventLevel.getName(),
                    DecisionSupportConfigEnum.HIGH_INCIDENCE_EVENT_GRADE_HB.getKey(), (StringUtils.isEmpty(maxEventLevel.getHb()) ? "0" : maxEventLevel.getHb()) + DecisionSupportConstants.PERCENT,
                    DecisionSupportConfigEnum.HIGH_INCIDENCE_EVENT_GRADE_TB.getKey(), (StringUtils.isEmpty(maxEventLevel.getTb()) ? "0" : maxEventLevel.getTb()) + DecisionSupportConstants.PERCENT,

                    DecisionSupportConfigEnum.HIGH_INCIDENCE_EMERGENCIES_NUMBER.getKey(), String.valueOf(total),
                    DecisionSupportConfigEnum.YEAR_MONTH_STATISTICAL.getKey(), currentLastMonthStr + DecisionSupportConstants.MONTH);
            result.setConclusionText(conclusionText);
        }

        // 设置月环比
        result.setMonthHbScale(StringUtils.isEmpty(maxEventType.getHb()) ? "-" : maxEventType.getHb());
        return result;
    }

    /**
     * 获取事件类型、等级
     *
     * @param maxEventType
     * @param maxEventLevel
     * @return
     */
    private List getCharData(HighIncidenceEventTypeDto maxEventType, HighIncidenceEventTypeDto maxEventLevel) {
        ArrayList<Object> result = Lists.newArrayList();
        if (!ObjectUtils.isEmpty(maxEventType)) {
            result.add(maxEventType);
        }
        if (!ObjectUtils.isEmpty(maxEventLevel)) {
            result.add(maxEventLevel);
        }
        return result;
    }

    /**
     * 寻找最大的对象
     *
     * @param lastMonth
     * @param lastLastMonth
     * @param lastYearLastMonth
     * @return
     */
    private HighIncidenceEventTypeDto findMaxObject(List<BaseVO> lastMonth, List<BaseVO> lastLastMonth, List<BaseVO> lastYearLastMonth) {
        HighIncidenceEventTypeDto tempDto = null;
        for (int i = 0; i < lastMonth.size(); i++) {
            HighIncidenceEventTypeDto highIncidenceEventTypeDto = JSONObject.parseObject(JSONObject.toJSONString(lastMonth.get(i)), HighIncidenceEventTypeDto.class);
            if (null == tempDto || Integer.parseInt(highIncidenceEventTypeDto.getValue()) > Integer.parseInt(tempDto.getValue())) {
                tempDto = highIncidenceEventTypeDto;
                tempDto.setIndex(i);
            }
        }

        // 最大的索引
        Integer index = tempDto.getIndex();

        String tb = "-";
        String hb = "-";

        if (!StringUtils.isEmpty(tempDto.getValue()) && 0 != Integer.parseInt(tempDto.getValue())) {
            tb = MathUtil.calPercent(
                    new BigDecimal(Integer.parseInt(tempDto.getValue()) - Integer.parseInt(lastYearLastMonth.get(index).getValue())),
                    new BigDecimal(tempDto.getValue()), 2).toString();

            hb = MathUtil.calPercent(
                    new BigDecimal(Integer.parseInt(tempDto.getValue()) - Integer.parseInt(lastLastMonth.get(index).getValue())),
                    new BigDecimal(tempDto.getValue()), 2).toString();
        }

        // 同比
        tempDto.setTb(tb);

        // 环比
        tempDto.setHb(hb);

        return tempDto;
    }
}
