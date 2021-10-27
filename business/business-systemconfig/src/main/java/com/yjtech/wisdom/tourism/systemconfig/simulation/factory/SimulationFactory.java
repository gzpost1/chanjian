package com.yjtech.wisdom.tourism.systemconfig.simulation.factory;

import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.SimulationCommonDto;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author 李波
 * @description: 模拟数据工厂类
 * @date 2021/7/1320:06
 */
public interface SimulationFactory<T extends SimulationCommonDto> {
    /**
     * 解析json
     * @param json
     * @param <T>
     * @return
     */
    public <T> T parse(String json);

    /**
     * 根据对象获取json
     * @param obj
     * @return
     */
    public String toJSONBytes(T obj);

    /**
     * 根据map获取json
     * @param map
     * @return
     */
    public default String toJSONBytes(Map<String,Object> map){return null;};

    /**
     * 同步平台信息返回对象获取json
     *
     * @param
     * @return
     */
    public default String syncPlatformToJSONBytes(Object object, List<String> cityList){return null;};

    /**
     * 根据配置生成模拟数据存储到redis中
     * @param obj
     */
    public void generateMockRedisData(T obj);

    public default String getCacheKey(String key){
        return Constants.SIMULATION_KEY + key;
    }

    /**
     * 比较
     * @param <T>
     * @param a
     * @param b
     * @return
     */
    public default <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
        if(a.size() != b.size())
            return false;
        Collections.sort(a);
        Collections.sort(b);
        for(int i=0;i<a.size();i++){
            if(!a.get(i).equals(b.get(i)))
                return false;
        }
        return true;
    }
}
