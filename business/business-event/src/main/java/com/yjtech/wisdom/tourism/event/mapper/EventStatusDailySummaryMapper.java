package com.yjtech.wisdom.tourism.event.mapper;

import com.yjtech.wisdom.tourism.event.entity.EventStatusDailySummaryEntity;
import com.yjtech.wisdom.tourism.event.query.EventSumaryQuery;
import com.yjtech.wisdom.tourism.mybatis.extension.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 事件-统计 Mapper 接口
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-12
 */
public interface EventStatusDailySummaryMapper extends MyBaseMapper<EventStatusDailySummaryEntity> {

    Integer queryQuantityByStatus(@Param("params") EventSumaryQuery query);
}
