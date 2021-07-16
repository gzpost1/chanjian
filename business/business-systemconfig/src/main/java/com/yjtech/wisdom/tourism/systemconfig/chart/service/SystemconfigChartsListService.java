package com.yjtech.wisdom.tourism.systemconfig.chart.service;

import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.list.SystemconfigChartsListCreateDto;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.list.SystemconfigChartsListQueryVo;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.list.SystemconfigChartsListVo;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.point.SystemconfigChartsPointVo;
import com.yjtech.wisdom.tourism.systemconfig.chart.entity.SystemconfigChartsPointEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.systemconfig.chart.mapper.SystemconfigChartsListMapper;
import com.yjtech.wisdom.tourism.systemconfig.chart.entity.SystemconfigChartsListEntity;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SystemconfigChartsListService extends ServiceImpl<SystemconfigChartsListMapper, SystemconfigChartsListEntity> {

    
    public int batchInsert(List<SystemconfigChartsListEntity> list) {
        return baseMapper.batchInsert(list);
    }

    public List<SystemconfigChartsListVo> queryChartsListsBYCharts(SystemconfigChartsListQueryVo queryVo) {
        return Optional.ofNullable(this.baseMapper.queryChartsListsBYCharts(queryVo)).orElse(new ArrayList<SystemconfigChartsListVo>());
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateListsPointsBYCharts(List<SystemconfigChartsListCreateDto> list) {
        //1 原来的点位配置
        Long chartId = list.get(0).getChartId();
        removeChartListBYChartId(chartId);

        //1.1  转换为实体
        List<SystemconfigChartsListEntity> collect = list.stream().map(e -> {
            SystemconfigChartsListEntity entity = new SystemconfigChartsListEntity();
            BeanUtils.copyProperties(e, entity);
            entity.setDeleted(Byte.valueOf("0"));
            entity.setCreateTime(new Date());
            entity.setCreateUser(SecurityUtils.getUserId());

            return entity;
        }).collect(Collectors.toList());

        //2 保存配置
        this.batchInsert(collect);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeChartListBYChartId(Long chartId){
        this.baseMapper.removeChartListBYChartId(chartId);
    }
}
