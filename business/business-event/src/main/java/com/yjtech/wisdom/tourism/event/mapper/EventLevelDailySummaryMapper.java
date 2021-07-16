package com.yjtech.wisdom.tourism.event.mapper;

import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.event.entity.EventLevelDailySummaryEntity;
import com.yjtech.wisdom.tourism.event.query.EventSumaryQuery;
import com.yjtech.wisdom.tourism.mybatis.extension.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 事件-级别统计 Mapper 接口
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-12
 */
public interface EventLevelDailySummaryMapper extends MyBaseMapper<EventLevelDailySummaryEntity> {

    List<BaseVO> queryEventLevel(@Param("params") EventSumaryQuery query);
}
