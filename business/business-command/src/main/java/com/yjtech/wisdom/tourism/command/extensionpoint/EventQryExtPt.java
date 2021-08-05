package com.yjtech.wisdom.tourism.command.extensionpoint;

import com.yjtech.wisdom.tourism.command.query.event.EventSumaryQuery;
import com.yjtech.wisdom.tourism.command.vo.event.EventTrendVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
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
     List<BaseVO> queryEventQuantity(EventSumaryQuery query);

    List<BaseValueVO> querySaleTrend(EventSumaryQuery query);

    List<BaseVO> queryEventType(EventSumaryQuery query);

    List<BaseVO> queryEventLevel(EventSumaryQuery query);
}
