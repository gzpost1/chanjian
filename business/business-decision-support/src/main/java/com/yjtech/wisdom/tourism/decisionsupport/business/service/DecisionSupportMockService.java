package com.yjtech.wisdom.tourism.decisionsupport.business.service;

import com.alibaba.fastjson.JSONObject;
import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.decisionsupport.SimulationDecisionSupportDTO;
import com.yjtech.wisdom.tourism.systemconfig.simulation.factory.SimulationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 模拟数据
 *
 * @author renguangqian
 * @date 2021/8/23 16:21
 */
@Service
public class DecisionSupportMockService implements SimulationFactory<SimulationDecisionSupportDTO> {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Object parse(String json) {
        return JSONObject.parseObject(json, SimulationDecisionSupportDTO.class);
    }

    @Override
    public String toJSONBytes(SimulationDecisionSupportDTO obj) {
        return JSONObject.toJSONString(obj);
    }

    @Override
    public void generateMockRedisData(SimulationDecisionSupportDTO obj) {
        redisTemplate.opsForValue().set(getCacheKey(SimulationConstants.DECISION), obj);
    }
}
