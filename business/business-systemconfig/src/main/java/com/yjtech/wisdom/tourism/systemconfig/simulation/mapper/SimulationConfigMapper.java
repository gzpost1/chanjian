package com.yjtech.wisdom.tourism.systemconfig.simulation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.systemconfig.simulation.entity.SimulationConfigEntity;

public interface SimulationConfigMapper extends BaseMapper<SimulationConfigEntity> {
    void deleteByDomainId(Integer domainId);
}