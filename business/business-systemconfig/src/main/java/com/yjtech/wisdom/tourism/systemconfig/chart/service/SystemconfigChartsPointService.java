package com.yjtech.wisdom.tourism.systemconfig.chart.service;

import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.point.SystemconfigChartsPointCreateDto;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.point.SystemconfigChartsPointVo;
import org.springframework.beans.BeanUtils;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.systemconfig.chart.entity.SystemconfigChartsPointEntity;
import com.yjtech.wisdom.tourism.systemconfig.chart.mapper.SystemconfigChartsPointMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SystemconfigChartsPointService extends ServiceImpl<SystemconfigChartsPointMapper, SystemconfigChartsPointEntity> {

    /**
     * 获取图表的点位配置
     * @param id
     * @return
     */
    public List<SystemconfigChartsPointVo> queryChartsPointsBYCharts(Long id) {
        return Optional.ofNullable(this.baseMapper.queryChartsPointsBYCharts(id)).orElse(new ArrayList<SystemconfigChartsPointVo>());
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateChartsPointsBYCharts(List<SystemconfigChartsPointCreateDto> list) {
        //1 原来的点位配置
        Long chartId = list.get(0).getChartId();
        removeChartPointBYChartId(chartId);

        //1.1  转换为实体
        List<SystemconfigChartsPointEntity> collect = list.stream().map(e -> {
            SystemconfigChartsPointEntity entity = new SystemconfigChartsPointEntity();
            BeanUtils.copyProperties(e, entity);
            entity.setDeleted(Byte.valueOf("0"));
            entity.setCreateTime(new Date());
            entity.setCreateUser(SecurityUtils.getUserId());

            return entity;
        }).collect(Collectors.toList());

        //2 保存配置
        this.baseMapper.insertList(collect);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeChartPointBYChartId(Long chartId){
        this.baseMapper.removeChartPointBYChartId(chartId);

    }

}
