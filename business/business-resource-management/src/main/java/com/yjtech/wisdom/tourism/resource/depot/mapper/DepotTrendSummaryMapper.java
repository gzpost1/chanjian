package com.yjtech.wisdom.tourism.resource.depot.mapper;

import com.yjtech.wisdom.tourism.mybatis.extension.MyBaseMapper;
import com.yjtech.wisdom.tourism.resource.depot.entity.DepotTrendSummaryEntity;
import com.yjtech.wisdom.tourism.resource.depot.entity.vo.DepotTrendVo;
import com.yjtech.wisdom.tourism.resource.depot.query.DepotSummaryQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface DepotTrendSummaryMapper extends MyBaseMapper<DepotTrendSummaryEntity> {

    /**
     *  车辆出入趋势
     * @param query
     * @return
     */
    List<DepotTrendVo> queryTrend(@Param("params") DepotSummaryQuery query);
}
