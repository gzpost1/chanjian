package com.yjtech.wisdom.tourism.command.extensionpoint.mock;

import com.yjtech.wisdom.tourism.command.extensionpoint.EventExtensionConstant;
import com.yjtech.wisdom.tourism.command.extensionpoint.EventQryExtPt;
import com.yjtech.wisdom.tourism.command.query.event.EventSumaryQuery;
import com.yjtech.wisdom.tourism.command.vo.event.EventTrendVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
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
    public List<BaseValueVO> querySaleTrend(EventSumaryQuery query) {
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
