package com.yjtech.wisdom.tourism.event.mapper;

import com.yjtech.wisdom.tourism.event.entity.EventDailySummaryEntity;
import com.yjtech.wisdom.tourism.event.query.EventSumaryQuery;
import com.yjtech.wisdom.tourism.event.vo.EventTrendVO;
import com.yjtech.wisdom.tourism.mybatis.extension.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 事件-统计 Mapper 接口
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-12
 */
public interface EventDailySummaryMapper extends MyBaseMapper<EventDailySummaryEntity> {

    Integer queryQuantity(@Param("params") EventSumaryQuery query);


    List<EventTrendVO> queryTrend(@Param("params") EventSumaryQuery query);
}
