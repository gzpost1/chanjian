package com.yjtech.wisdom.tourism.resource.depot.mapper;

import com.yjtech.wisdom.tourism.mybatis.extension.MyBaseMapper;
import com.yjtech.wisdom.tourism.resource.depot.entity.DepotTypeSummaryEntity;
import com.yjtech.wisdom.tourism.resource.depot.entity.vo.DepotTypeVo;
import com.yjtech.wisdom.tourism.resource.depot.query.DepotSummaryQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepotTypeSummaryMapper extends MyBaseMapper<DepotTypeSummaryEntity> {

    List<DepotTypeVo> queryType(@Param("params") DepotSummaryQuery query);
}
