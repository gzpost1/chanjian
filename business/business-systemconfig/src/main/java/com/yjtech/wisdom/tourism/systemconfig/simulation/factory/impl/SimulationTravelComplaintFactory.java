package com.yjtech.wisdom.tourism.systemconfig.simulation.factory.impl;

import com.alibaba.fastjson.JSONObject;
import com.yjtech.wisdom.tourism.common.constant.CacheKeyContants;
import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.complaint.SimulationTravelComplaintDTO;
import com.yjtech.wisdom.tourism.systemconfig.simulation.factory.SimulationFactory;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 旅游投诉 模拟数据 模板构建
 *
 * @date 2021/8/21 11:19
 * @author horadirm
 */
@Component(value = SimulationConstants.TRAVEL_COMPLAINT)
public class SimulationTravelComplaintFactory implements SimulationFactory<SimulationTravelComplaintDTO> {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public Object parse(String json) {
        return JSONObject.parseObject(json, SimulationTravelComplaintDTO.class);
    }

    @Override
    public String toJSONBytes(SimulationTravelComplaintDTO obj) {
        return JSONObject.toJSONString(obj);
    }

    @SneakyThrows
    @Override
    public void generateMockRedisData(SimulationTravelComplaintDTO obj) {
        //随机数
        int randomInt = (int) (-20 + Math.random() * (20 - (-20) + 1));
        obj.setRandomNumber(String.valueOf(randomInt));
        //日投诉量（随机数/10+输入值）
        obj.setDayOfComplaint(new BigDecimal(obj.getRandomNumber()).divide(BigDecimal.TEN, 0, BigDecimal.ROUND_HALF_UP).add(obj.getDayOfComplaint()));
        //月累计投诉量（日投诉量*当前日期号数+输入值）
        obj.setMonthOfComplaintTotal(obj.getDayOfComplaint().multiply(new BigDecimal(LocalDate.now().getDayOfMonth())).add(obj.getMonthOfComplaintTotal()).setScale(0,BigDecimal.ROUND_HALF_UP));

        redisTemplate.opsForValue().set(getCacheKey(SimulationConstants.TRAVEL_COMPLAINT), obj);

        //删除旅游投诉模拟数据结果缓存key
//        redisTemplate.delete(redisTemplate.keys(CacheKeyContants.TRAVEL_COMPLAINT_SIMULATION_PREFIX + "*"));

    }

}
