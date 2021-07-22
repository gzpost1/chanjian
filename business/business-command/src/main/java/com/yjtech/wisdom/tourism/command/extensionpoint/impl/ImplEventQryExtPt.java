package com.yjtech.wisdom.tourism.command.extensionpoint.impl;

import com.yjtech.wisdom.tourism.command.extensionpoint.EventExtensionConstant;
import com.yjtech.wisdom.tourism.command.extensionpoint.EventQryExtPt;
import com.yjtech.wisdom.tourism.command.query.event.EventSumaryQuery;
import com.yjtech.wisdom.tourism.command.service.event.EventService;
import com.yjtech.wisdom.tourism.command.vo.event.EventTrendVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
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
    private EventService eventService;


    @Override
    public List<BaseVO> queryEventQuantity() {
        return eventService.queryEventQuantity();
    }

    @Override
    public List<EventTrendVO> querySaleTrend(EventSumaryQuery query) {
        return eventService.getBaseMapper().queryTrend(query);
    }

    @Override
    public List<BaseVO> queryEventType(EventSumaryQuery query) {
        return eventService.queryEventType(query);
    }

    @Override
    public List<BaseVO> queryEventLevel(EventSumaryQuery query) {
        return eventService.queryEventLevel(query);
    }


}
