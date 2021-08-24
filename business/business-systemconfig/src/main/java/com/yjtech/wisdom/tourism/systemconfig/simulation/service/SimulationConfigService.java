package com.yjtech.wisdom.tourism.systemconfig.simulation.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.SimulationCommonDto;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.SimulationQueryDto;
import com.yjtech.wisdom.tourism.systemconfig.simulation.entity.SimulationConfigEntity;
import com.yjtech.wisdom.tourism.systemconfig.simulation.enums.SimulationEnum;
import com.yjtech.wisdom.tourism.systemconfig.simulation.factory.SimulationFactory;
import com.yjtech.wisdom.tourism.systemconfig.simulation.mapper.SimulationConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Service
public class SimulationConfigService extends ServiceImpl<SimulationConfigMapper, SimulationConfigEntity> {

    @Autowired
    private Map<String, SimulationFactory> factoryMap;

    /**
     * 查询详情
     *
     * @param query
     * @return
     */
    public Object queryForDetail(SimulationQueryDto query) {
        //1 根据doman查询配置
        LambdaQueryWrapper<SimulationConfigEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SimulationConfigEntity::getDomainId, query.getDomainId());
        SimulationConfigEntity entity = this.baseMapper.selectOne(queryWrapper);

        if (Objects.nonNull(entity)) {
            return getFactory(query.getDomainId()).parse(entity.getConfigData());
        }

        return null;
    }

    /**
     * 存储配置
     *
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveUpdated(SimulationCommonDto dto) {
        //1 删除原来的配置
        this.baseMapper.deleteByDomainId(dto.getDomainId());

        //2  新增配置
        String configData = getFactory(dto.getDomainId()).toJSONBytes(dto);
        SimulationConfigEntity entity = new SimulationConfigEntity();
        entity.setDomainId(dto.getDomainId());
        entity.setConfigData(configData);
        entity.setDeleted(Byte.valueOf("0"));
        this.save(entity);

        //3  计算模拟数据并写入redis
        getFactory(dto.getDomainId()).generateMockRedisData(dto);
    }
    public void refreshData(SimulationQueryDto query) {
        //3  计算模拟数据并写入redis
        getFactory(query.getDomainId()).generateMockRedisData((SimulationCommonDto)queryForDetail(query));
    }


    /**
     * 根据domainid获取实现类
     *
     * @param domainId
     * @return
     */
    private SimulationFactory getFactory(Integer domainId) {
        return factoryMap.get(getSimulationName(domainId));
    }

    /**
     * 根据domainid获取注入的类名称
     *
     * @param domainId
     * @return
     */
    private String getSimulationName(Integer domainId) {
        return SimulationEnum.getSimulationNameByType(domainId);
    }

    public void generateRandom(SimulationCommonDto dto) {
        //随机数
        int randomInt = (int) (-20 + Math.random() * (20 - (-20) + 1));
        dto.setRandomNumber(String.valueOf(randomInt));
    }
}
