package com.yjtech.wisdom.tourism.event.mapper;

import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.event.entity.EventTypeDailySummaryEntity;
import com.yjtech.wisdom.tourism.event.query.EventSumaryQuery;
import com.yjtech.wisdom.tourism.mybatis.extension.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 事件-类型统计 Mapper 接口
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-12
 */
public interface EventTypeDailySummaryMapper extends MyBaseMapper<EventTypeDailySummaryEntity> {

    List<BasePercentVO> queryEventType(@Param("params") EventSumaryQuery query);
}
