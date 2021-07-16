package com.yjtech.wisdom.tourism.event.extensionpoint.mock;

import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.event.extensionpoint.EventExtensionConstant;
import com.yjtech.wisdom.tourism.event.extensionpoint.EventQryExtPt;
import com.yjtech.wisdom.tourism.event.query.EventSumaryQuery;
import com.yjtech.wisdom.tourism.event.vo.EventTrendVO;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;

import java.util.List;

/**
 * 事务模拟数据扩展点
 * @author xulei
 * @create 2021-07-14 14:56
 */
@Extension(bizId = ExtensionConstant.BIZ_EVENT,
        useCase = EventExtensionConstant.EVENT_QUANTITY,
        scenario = ExtensionConstant.SCENARIO_MOCK)
public class MockEventQryExtPt implements EventQryExtPt {

    @Override
    public List<BaseVO> queryEventQuantity() {
        return null;
    }

    @Override
    public List<EventTrendVO> querySaleTrend(EventSumaryQuery query) {
        return null;
    }

    @Override
    public List<BaseVO> queryEventType(EventSumaryQuery query) {
        return null;
    }

    @Override
    public List<BaseVO> queryEventLevel(EventSumaryQuery query) {
        return null;
    }
}
