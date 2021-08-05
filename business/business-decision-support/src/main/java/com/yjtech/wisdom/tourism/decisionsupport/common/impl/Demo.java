package com.yjtech.wisdom.tourism.decisionsupport.common.impl;

import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnEntity;
import com.yjtech.wisdom.tourism.decisionsupport.common.strategy.BaseStrategy;
import org.springframework.stereotype.Component;

/**
 * @author renguangqian
 * @date 2021/8/5 10:59
 */
@Component
public class Demo implements BaseStrategy {

    /**
     * 自行实现数据组装后被反射调用的方法
     *
     * @return
     */
    @Override
    public DecisionWarnEntity init() {
        System.out.println("执行到了Demo");
        return DecisionWarnEntity.builder().warnNum("XXXX").build();
    }
}
