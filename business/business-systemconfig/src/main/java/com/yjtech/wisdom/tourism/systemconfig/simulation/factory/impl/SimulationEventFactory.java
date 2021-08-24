package com.yjtech.wisdom.tourism.systemconfig.simulation.factory.impl;

import com.alibaba.fastjson.JSON;
import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.event.SimulationEventDto;
import com.yjtech.wisdom.tourism.systemconfig.simulation.factory.SimulationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liuhong
 * @description: 票务
 * @date 2021/7/1412:07
 */
@Component(value = SimulationConstants.EVENT)
public class SimulationEventFactory implements SimulationFactory<SimulationEventDto> {
    @Autowired
    private RedisCache redisCache;

    @Override
    public Object parse(String json) {
        return JSON.parseObject(json, SimulationEventDto.class);
    }

    @Override
    public String toJSONBytes(SimulationEventDto obj) {
        return JSON.toJSONString(obj);
    }

    @Override
    public void generateMockRedisData(SimulationEventDto obj) {
        redisCache.setCacheObject(getCacheKey(SimulationConstants.EVENT), obj);
    }

}
