package com.yjtech.wisdom.tourism.integration.extensionpoint.mock;

import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.integration.extensionpoint.OneTravelExtensionConstant;
import com.yjtech.wisdom.tourism.integration.extensionpoint.OneTravelQryExtPt;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 一码游相关 模拟数据 扩展
 *
 * @author horadirm
 * @date 2021/8/21 13:39
 */
@Extension(bizId = ExtensionConstant.ONE_TRAVEL,
        useCase = OneTravelExtensionConstant.HOTEL_QUANTITY,
        scenario = ExtensionConstant.SCENARIO_MOCK)
public class MockOneTravelQryExtPt implements OneTravelQryExtPt {

    @Autowired
    private RedisCache redisCache;




}
