package com.yjtech.wisdom.tourism.resource.comment.extensionpoint.mock;

import com.alibaba.fastjson.JSONObject;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.resource.comment.extensionpoint.PraiseExtensionConstant;
import com.yjtech.wisdom.tourism.resource.comment.extensionpoint.PraiseWordQryExtPt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

/**
 * 票务模拟数据扩展点
 *
 * @author liuhong
 * @date 2021-07-13 11:12
 */
@Extension(bizId = ExtensionConstant.BIZ_PRAISE,
        useCase = PraiseExtensionConstant.USE_CASE_PRAISE_WORD,
        scenario = ExtensionConstant.SCENARIO_MOCK)
public class MockPraiseWordQryExt implements PraiseWordQryExtPt {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> queryHotWordBYComments() {
        Map<String, Object> map = JSONObject.parseObject(redisTemplate.opsForValue().get(Constants.SIMULATION_KEY + SimulationConstants.PRAISE) + "", Map.class);
        if (map != null && map.containsKey("five")) {
            return (Map<String, Object>) map.get("five");
        }

        return null;
    }
}
