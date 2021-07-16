package com.yjtech.wisdom.tourism.systemconfig.simulation.factory.impl;

import com.alibaba.fastjson.JSON;
import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.praise.SimulationPraiseDto;
import com.yjtech.wisdom.tourism.systemconfig.simulation.factory.SimulationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liuhong
 * @description: 票务
 * @date 2021/7/1412:07
 */
@Component(value = SimulationConstants.TICKET)
public class SimulationTicketFactory implements SimulationFactory<SimulationPraiseDto> {
    @Autowired
    private RedisCache redisCache;

    @Override
    public Object parse(String json) {
        return JSON.parseObject(json, SimulationPraiseDto.class);
    }

    @Override
    public String toJSONBytes(SimulationPraiseDto obj) {
        return JSON.toJSONString(obj);
    }

    @Override
    public void generateMockRedisData(SimulationPraiseDto obj) {
        redisCache.setCacheObject(getCacheKey(SimulationConstants.TICKET), obj);
    }

}
