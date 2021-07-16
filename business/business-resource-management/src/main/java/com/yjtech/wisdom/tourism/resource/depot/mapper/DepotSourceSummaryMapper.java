package com.yjtech.wisdom.tourism.resource.depot.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.mybatis.extension.MyBaseMapper;
import com.yjtech.wisdom.tourism.resource.depot.entity.DepotSourceSummaryEntity;
import com.yjtech.wisdom.tourism.resource.depot.entity.dto.DepotSourceBaseDto;
import com.yjtech.wisdom.tourism.resource.depot.entity.vo.DepotBaseVo;
import com.yjtech.wisdom.tourism.resource.depot.query.DepotPageSummaryQuery;
import com.yjtech.wisdom.tourism.resource.depot.query.DepotSummaryQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepotSourceSummaryMapper extends MyBaseMapper<DepotSourceSummaryEntity> {

    List<DepotBaseVo> queryDistributionMaps(@Param("params") DepotSummaryQuery query);

    IPage<DepotBaseVo> querySourceOfProvince(Page page, @Param("params") DepotPageSummaryQuery query);

    IPage<DepotBaseVo> querySourceOfCity(Page page, @Param("params") DepotPageSummaryQuery query);
}
