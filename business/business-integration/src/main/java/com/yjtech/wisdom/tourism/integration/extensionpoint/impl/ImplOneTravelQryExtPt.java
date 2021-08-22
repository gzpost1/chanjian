package com.yjtech.wisdom.tourism.integration.extensionpoint.impl;

import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.integration.extensionpoint.OneTravelExtensionConstant;
import com.yjtech.wisdom.tourism.integration.extensionpoint.OneTravelQryExtPt;

/**
 * 一码游相关 真实数据 扩展
 *
 * @author horadirm
 * @date 2021/8/21 10:44
 */
@Extension(bizId = ExtensionConstant.ONE_TRAVEL,
        useCase = OneTravelExtensionConstant.HOTEL_QUANTITY,
        scenario = ExtensionConstant.SCENARIO_IMPL)
public class ImplOneTravelQryExtPt implements OneTravelQryExtPt {


}
