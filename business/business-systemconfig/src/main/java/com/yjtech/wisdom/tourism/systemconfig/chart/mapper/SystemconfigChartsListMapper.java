package com.yjtech.wisdom.tourism.systemconfig.chart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.list.SystemconfigChartsListQueryVo;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.list.SystemconfigChartsListVo;
import com.yjtech.wisdom.tourism.systemconfig.chart.entity.SystemconfigChartsListEntity;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemconfigChartsListMapper extends BaseMapper<SystemconfigChartsListEntity> {
    int batchInsert(@Param("list") List<SystemconfigChartsListEntity> list);

    List<SystemconfigChartsListVo> queryChartsListsBYCharts(@Param("params") SystemconfigChartsListQueryVo queryVo);

    void removeChartListBYChartId(Long chartId);

}