package com.yjtech.wisdom.tourism.systemconfig.simulation.factory.impl;

import com.alibaba.fastjson.JSONObject;
import com.yjtech.wisdom.tourism.common.constant.CacheKeyContants;
import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.onetravel.SimulationOneTravelDTO;
import com.yjtech.wisdom.tourism.systemconfig.simulation.factory.SimulationFactory;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
        //随机数
        int randomInt = (int) (-20 + Math.random() * (20 - (-20) + 1));
        obj.setRandomNumber(String.valueOf(randomInt));
        //小时访问数
        obj.setHourOfVisit(obj.getHourOfInitVisit() + randomInt);
        //日累计访问数-结果
        obj.setDayOfTotalVisitResult(obj.getHourOfVisit() * LocalDateTime.now().getHour() + obj.getDayOfTotalVisit());
        //昨日访问数
        obj.setYesterdayVisit(new BigDecimal(obj.getHourOfVisit()).multiply(obj.getYesterdayVisitCoefficient()).setScale(0, BigDecimal.ROUND_HALF_UP).intValue() + obj.getYesterdayVisitAdditions());
        //昨日活跃用户数
        obj.setYesterdayActive(new BigDecimal(obj.getYesterdayVisit()).multiply(obj.getYesterdayActiveCoefficient()).setScale(0, BigDecimal.ROUND_HALF_UP).intValue() + obj.getYesterdayActiveAdditions());
        //使用总人数
        obj.setUserTotal(new BigDecimal(obj.getYesterdayVisit()).multiply(obj.getUserTotalCoefficient()).setScale(0, BigDecimal.ROUND_HALF_UP).intValue() + obj.getUserTotalAdditions());
        //总访问数
        obj.setVisitTotal(new BigDecimal(obj.getDayOfTotalVisitResult()).multiply(obj.getVisitTotalCoefficient()).setScale(0, BigDecimal.ROUND_HALF_UP).intValue() + obj.getVisitTotalAdditions());

        redisTemplate.opsForValue().set(getCacheKey(SimulationConstants.ONE_TRAVEL), obj);

        //删除一码游模拟数据结果缓存key
        redisTemplate.delete(redisTemplate.keys(CacheKeyContants.ONE_TRAVEL_SIMULATION_PREFIX + "*"));

    }

}
