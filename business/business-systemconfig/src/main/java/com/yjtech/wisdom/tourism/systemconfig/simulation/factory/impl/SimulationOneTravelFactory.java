package com.yjtech.wisdom.tourism.systemconfig.simulation.factory.impl;

import com.alibaba.fastjson.JSONObject;
import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.onetravel.SimulationOneTravelDTO;
import com.yjtech.wisdom.tourism.systemconfig.simulation.factory.SimulationFactory;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 一码游 模拟数据 模板构建
 *
 * @date 2021/8/21 11:19
 * @author horadirm
 */
@Component(value = SimulationConstants.ONE_TRAVEL)
public class SimulationOneTravelFactory implements SimulationFactory<SimulationOneTravelDTO> {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public Object parse(String json) {
        return JSONObject.parseObject(json, SimulationOneTravelDTO.class);
    }

    @Override
    public String toJSONBytes(SimulationOneTravelDTO obj) {
        return JSONObject.toJSONString(obj);
    }

    @SneakyThrows
    @Override
    public void generateMockRedisData(SimulationOneTravelDTO obj) {

        redisTemplate.opsForValue().set(getCacheKey(SimulationConstants.ONE_TRAVEL), obj);

    }

}
