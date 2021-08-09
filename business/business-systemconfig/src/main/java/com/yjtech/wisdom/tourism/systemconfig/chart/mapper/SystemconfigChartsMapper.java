package com.yjtech.wisdom.tourism.systemconfig.chart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.MenuChartQueryDto;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.SystemConfigChartQueryPageDto;
import com.yjtech.wisdom.tourism.systemconfig.chart.entity.SystemconfigChartsEntity;
import com.yjtech.wisdom.tourism.systemconfig.chart.vo.SystemconfigChartsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemconfigChartsMapper extends BaseMapper<SystemconfigChartsEntity> {
    /**
     * 分页
     *
     * @param page
     * @param query
     * @return
     */
    IPage<SystemconfigChartsVo> queryForPage(Page page, @Param("params") SystemConfigChartQueryPageDto query);

    List<SystemconfigChartsVo> queryChartListForMenu(@Param("params") MenuChartQueryDto queryDto);

    Integer findChartMenusNum(Long id);

    Integer findMenuIsExistChart(Long id);
}