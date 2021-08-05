package com.yjtech.wisdom.tourism.decisionsupport.common.strategy;


/**
 * 策略执行器
 *
 * @author renguangqian
 * @date 2021/8/5 14:55
 */
public interface StrategyExecute {

    /**
     * 执行方法
     *
     * @param e
     * @return
     */
    Object execute(Enum e);
}
