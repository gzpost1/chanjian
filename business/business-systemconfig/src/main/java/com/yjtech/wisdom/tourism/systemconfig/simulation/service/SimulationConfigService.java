package com.yjtech.wisdom.tourism.systemconfig.simulation.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.dto.area.AreaInfoVO;
import com.yjtech.wisdom.tourism.system.domain.Platform;
import com.yjtech.wisdom.tourism.system.service.AreaService;
import com.yjtech.wisdom.tourism.system.service.PlatformService;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.SimulationCommonDto;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.SimulationQueryDto;
import com.yjtech.wisdom.tourism.systemconfig.simulation.entity.SimulationConfigEntity;
import com.yjtech.wisdom.tourism.systemconfig.simulation.enums.SimulationEnum;
import com.yjtech.wisdom.tourism.systemconfig.simulation.factory.SimulationFactory;
import com.yjtech.wisdom.tourism.systemconfig.simulation.mapper.SimulationConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SimulationConfigService extends ServiceImpl<SimulationConfigMapper, SimulationConfigEntity> {

    @Autowired
    private Map<String, SimulationFactory> factoryMap;
    @Autowired
    private PlatformService platformService;
    @Autowired
    private AreaService areaService;

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

    /**
     * 同步平台信息 true:同步，false:不需要同步
     *
     * @param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean syncSimulation() {
        boolean result = false;
        //获取平台信息
        Platform platform = platformService.getPlatform();
        //获取所有区域信息
        AreaInfoVO areaInfoByCode = areaService.getAreaInfoByCode(platform.getAreaCode());
        //根据省级code查询地市列表
        List<AreaInfoVO> levelCityList = areaService.getPrefectureLevelCityList(areaInfoByCode.getProvinceCode());

        List<String> levelCityNameList = levelCityList.stream().filter(Objects::nonNull).map(AreaInfoVO::getCityName).collect(Collectors.toList());
        //同步停车场、票务数据（只有这两个模块有省内数据）
        List<Integer> domainIds = Lists.newArrayList();
        domainIds.add(SimulationEnum.TICKET.getType());
        domainIds.add(SimulationEnum.DEPORT.getType());
//        for (Integer item : domainIds) {
//            Object obj = queryForDetail(SimulationQueryDto.builder().domainId(item).build());
//            String configData = getFactory(item).syncPlatformToJSONBytes(obj, levelCityNameList);
//            if(StringUtils.isNotBlank(configData)){
//                this.baseMapper.deleteByDomainId(item);
//                SimulationConfigEntity entity = new SimulationConfigEntity();
//                entity.setDomainId(item);
//                entity.setConfigData(configData);
//                entity.setDeleted(Byte.valueOf("0"));
//                this.save(entity);
//                result = true;
//            }
//        }
        return result;
    }
}
