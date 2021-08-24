package com.yjtech.wisdom.tourism.systemconfig.simulation.factory.impl;

import com.alibaba.fastjson.JSONObject;
import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.hotel.SimulationHotelDTO;
import com.yjtech.wisdom.tourism.systemconfig.simulation.factory.SimulationFactory;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 酒店民宿 模拟数据 模板构建
 *
 * @date 2021/8/21 11:19
 * @author horadirm
 */
@Component(value = SimulationConstants.HOTEL)
public class SimulationHotelFactory implements SimulationFactory<SimulationHotelDTO> {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public Object parse(String json) {
        return JSONObject.parseObject(json, SimulationHotelDTO.class);
    }

    @Override
    public String toJSONBytes(SimulationHotelDTO obj) {
        return JSONObject.toJSONString(obj);
    }

    @SneakyThrows
    @Override
    public void generateMockRedisData(SimulationHotelDTO obj) {
        //随机数
        int randomInt = (int) (-20 + Math.random() * (20 - (-20) + 1));
        obj.setRandomNumber(String.valueOf(randomInt));
        //评分
        obj.setRate(obj.getInitRate().add(new BigDecimal(Integer.valueOf(obj.getRandomNumber())/10)));
        //日累计评价量（日评价初始数+随机数）
        obj.setDayOfTotalCount(obj.getDayOfInitCount() + Integer.valueOf(obj.getRandomNumber()));
        //月累计投诉量（日累计评价量*当前日期号数+输入值）
        obj.setMonthOfEvaluateTotal(new BigDecimal(obj.getDayOfTotalCount()).multiply(new BigDecimal(LocalDate.now().getDayOfMonth())).add(obj.getMonthOfEvaluateTotal()).setScale(0,BigDecimal.ROUND_HALF_UP));
        //评价类型分布-好评
        obj.setGoodRatePercent(obj.getInitGoodRatePercent().add(new BigDecimal(Integer.valueOf(obj.getRandomNumber())/5)).add(new BigDecimal(Integer.valueOf(obj.getRandomNumber())/100)).setScale(1,BigDecimal.ROUND_HALF_UP));
        //评价类型分布-中评
        obj.setMediumRatePercent(new BigDecimal(100).subtract(obj.getGoodRatePercent()).divide(new BigDecimal(2),1,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(obj.getRandomNumber())));
        //评价类型分布-差评
        obj.setBadRatePercent(new BigDecimal(100).subtract(obj.getGoodRatePercent()).subtract(obj.getMediumRatePercent()));
        //历史平均价
        obj.setAveragePrice((obj.getHighestPrice().add(obj.getLowestPrice())).divide(new BigDecimal(2),1, BigDecimal.ROUND_HALF_UP));

        redisTemplate.opsForValue().set(getCacheKey(SimulationConstants.HOTEL), obj);

    }

}
