package com.yjtech.wisdom.tourism.resource.depot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.resource.depot.entity.DepotSourceSummaryEntity;
import com.yjtech.wisdom.tourism.resource.depot.entity.dto.DepotSourceBaseDto;
import com.yjtech.wisdom.tourism.resource.depot.entity.vo.DepotBaseVo;
import com.yjtech.wisdom.tourism.resource.depot.mapper.DepotSourceSummaryMapper;
import com.yjtech.wisdom.tourism.resource.depot.query.DepotPageSummaryQuery;
import com.yjtech.wisdom.tourism.resource.depot.query.DepotSummaryQuery;
import com.yjtech.wisdom.tourism.resource.depot.query.DepotTimeTypeAndPageQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 停车场来源地业务处理
 * @Author zc
 * @Date 2021-07-04 11:40
 */
@Service
public class DepotSourceSummaryService extends ServiceImpl<DepotSourceSummaryMapper, DepotSourceSummaryEntity> {

    /**
     * @Description:  地图分布
     * @Param:  来源 0-市 1-省
     * @return: List
     * @Author: zc
     * @Date: 2021-07-05
     */
    public List<DepotBaseVo> queryDistributionMaps(DepotSummaryQuery query) {
        return baseMapper.queryDistributionMaps(query);
    }

    /** 
     * @Description: 车辆来源省份
     * @Param:  timeType 1-年 2-月 3-周  4-日
     * @return:  List
     * @Author: zc
     * @Date: 2021-07-05 
     */
    public IPage<DepotBaseVo> querySourceOfProvince(DepotPageSummaryQuery query){
        return baseMapper.querySourceOfProvince(new Page<>(query.getPageNo(), query.getPageSize()), query);
    }

    /**
     * @Description: 车辆来源地市
     * @Param:  timeType 1-年 2-月 3-周  4-日
     * @return:  List
     * @Author: zc
     * @Date: 2021-07-05
     */
    public IPage<DepotBaseVo> querySourceOfCity(DepotPageSummaryQuery query){
        return baseMapper.querySourceOfCity(new Page<>(query.getPageNo(), query.getPageSize()), query);
    }
}
