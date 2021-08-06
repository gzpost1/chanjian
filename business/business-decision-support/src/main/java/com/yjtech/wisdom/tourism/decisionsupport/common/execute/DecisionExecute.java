package com.yjtech.wisdom.tourism.decisionsupport.common.execute;

import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnEntity;
import com.yjtech.wisdom.tourism.decisionsupport.common.instance.DecisionStrategyEnum;
import com.yjtech.wisdom.tourism.decisionsupport.common.factory.DecisionStrategyFactory;
import com.yjtech.wisdom.tourism.decisionsupport.common.strategy.StrategyExecute;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 决策处理
 *
 * @author renguangqian
 * @date 2021/8/5 11:14
 */
public class DecisionExecute implements StrategyExecute {

    private static DecisionExecute instance = new DecisionExecute();

    private DecisionExecute (){}

        /**
         * 执行方法
         *
         * @param strategyEnum
         * @return
         */
    @Override
    public DecisionWarnEntity execute(Enum strategyEnum) {
        // 使用工厂，创建出需要的对象实例
        Object obj = DecisionStrategyFactory.getInstance().execute(strategyEnum);

        // 拿到对象后 通过反射执行 init 方法，获得结果对象
        try {
            Method initMethod = obj.getClass().getMethod("init");
            Object invoke = initMethod.invoke(obj);
            return (DecisionWarnEntity) invoke;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * 获得结果
     *
     * @param strategyEnum
     * @return
     */
    public static DecisionWarnEntity get(DecisionStrategyEnum strategyEnum) {
        return instance.execute(strategyEnum);
    }

}
