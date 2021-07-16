package com.yjtech.wisdom.tourism.event.extensionpoint;

import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.event.query.EventSumaryQuery;
import com.yjtech.wisdom.tourism.event.vo.EventTrendVO;
import com.yjtech.wisdom.tourism.extension.ExtensionPointI;

import java.util.List;

/**
 * @author xulei
 * @create 2021-07-14 14:52
 */
public interface EventQryExtPt extends ExtensionPointI {

    /**
     * 应急事件统计
     * @return
     */
     List<BaseVO> queryEventQuantity();

    List<EventTrendVO> querySaleTrend(EventSumaryQuery query);

    List<BaseVO> queryEventType(EventSumaryQuery query);

    List<BaseVO> queryEventLevel(EventSumaryQuery query);
}
