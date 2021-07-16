package com.yjtech.wisdom.tourism.resource.depot.mapper;

import com.yjtech.wisdom.tourism.mybatis.extension.MyBaseMapper;
import com.yjtech.wisdom.tourism.resource.depot.entity.DepotStaySummaryEntity;
import com.yjtech.wisdom.tourism.resource.depot.entity.vo.DepotStayVo;
import com.yjtech.wisdom.tourism.resource.depot.entity.vo.DepotTrendVo;
import com.yjtech.wisdom.tourism.resource.depot.query.DepotSummaryQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepotStaySummaryMapper extends MyBaseMapper<DepotStaySummaryEntity> {

    List<DepotStayVo> queryStay(@Param("params") DepotSummaryQuery query);
}
