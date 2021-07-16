package com.yjtech.wisdom.tourism.event.extensionpoint.impl;

import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.event.extensionpoint.EventExtensionConstant;
import com.yjtech.wisdom.tourism.event.extensionpoint.EventQryExtPt;
import com.yjtech.wisdom.tourism.event.query.EventSumaryQuery;
import com.yjtech.wisdom.tourism.event.service.EventDailySummaryService;
import com.yjtech.wisdom.tourism.event.service.EventLevelDailySummaryService;
import com.yjtech.wisdom.tourism.event.service.EventTypeDailySummaryService;
import com.yjtech.wisdom.tourism.event.vo.EventTrendVO;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 事务模拟数据扩展点
 * @author xulei
 * @create 2021-07-14 14:56
 */
@Extension(bizId = ExtensionConstant.BIZ_EVENT,
        useCase = EventExtensionConstant.EVENT_QUANTITY,
        scenario = ExtensionConstant.SCENARIO_IMPL)
public class ImplEventQryExtPt implements EventQryExtPt {


    @Autowired
    private EventDailySummaryService eventDailySummaryService;

    @Autowired
    private EventTypeDailySummaryService eventTypeDailySummaryService;

    @Autowired
    private EventLevelDailySummaryService eventLevelDailySummaryService;

    @Override
    public List<BaseVO> queryEventQuantity() {
        return eventDailySummaryService.queryEventQuantity();
    }

    @Override
    public List<EventTrendVO> querySaleTrend(EventSumaryQuery query) {
        return eventDailySummaryService.getBaseMapper().queryTrend(query);
    }

    @Override
    public List<BaseVO> queryEventType(EventSumaryQuery query) {
        return eventTypeDailySummaryService.queryEventType(query);
    }

    @Override
    public List<BaseVO> queryEventLevel(EventSumaryQuery query) {
        return eventLevelDailySummaryService.queryEventLevel(query);
    }


}
