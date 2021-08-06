package com.yjtech.wisdom.tourism.decisionsupport.common.factory;

import com.yjtech.wisdom.tourism.decisionsupport.common.instance.DecisionStrategyEnum;
import com.yjtech.wisdom.tourism.decisionsupport.common.strategy.StrategyFactory;

/**
 * 辅助决策-策略工厂
 *
 * @author renguangqian
 * @date 2021/8/5 11:25
 */
public class DecisionStrategyFactory extends StrategyFactory {

    private static class SingletonHolder {
        private static final DecisionStrategyFactory INSTANCE = new DecisionStrategyFactory();
    }

    private DecisionStrategyFactory (){}

    public static final DecisionStrategyFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public Object execute(Enum strategyEnum) {
        // 如果 策略枚举是 辅助决策的枚举，则进行强转
        if (strategyEnum instanceof DecisionStrategyEnum) {
            DecisionStrategyEnum decisionStrategyEnum = (DecisionStrategyEnum) strategyEnum;

            // 获得目标实例
            return decisionStrategyEnum.getInstance();
        }
        return null;
    }
}
