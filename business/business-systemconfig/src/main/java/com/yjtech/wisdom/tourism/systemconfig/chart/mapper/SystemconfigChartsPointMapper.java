package com.yjtech.wisdom.tourism.systemconfig.chart.mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.point.SystemconfigChartsPointVo;
import com.yjtech.wisdom.tourism.systemconfig.chart.entity.SystemconfigChartsPointEntity;

import java.util.List;

public interface SystemconfigChartsPointMapper extends BaseMapper<SystemconfigChartsPointEntity> {
    List<SystemconfigChartsPointVo> queryChartsPointsBYCharts(Long id);

    void removeChartPointBYChartId(Long chartId);

    int insertList(@Param("list")List<SystemconfigChartsPointEntity> list);


}