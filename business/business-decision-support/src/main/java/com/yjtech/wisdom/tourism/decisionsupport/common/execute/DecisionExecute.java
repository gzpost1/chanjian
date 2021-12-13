package com.yjtech.wisdom.tourism.decisionsupport.common.execute;

import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnEntity;
import com.yjtech.wisdom.tourism.decisionsupport.common.factory.DecisionStrategyFactory;
import com.yjtech.wisdom.tourism.decisionsupport.business.instance.DecisionStrategyEnum;
import com.yjtech.wisdom.tourism.decisionsupport.common.strategy.StrategyExecute;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 决策处理
 *
 * @author renguangqian
 * @date 2021/8/5 11:14
 */
public class DecisionExecute extends StrategyExecute {

    private static final String INIT_METHOD_NAME = "init";

    private static DecisionExecute instance = new DecisionExecute();

    private DecisionExecute (){}

        /**
         * 执行方法
         *
         * @param strategyEnum
         * @return
         */
    @Override
    public DecisionWarnEntity execute(Enum strategyEnum, DecisionEntity entity, Byte isSimulation) {
        // 使用工厂，创建出需要的对象实例
        Object obj = DecisionStrategyFactory.getInstance().execute(strategyEnum);

        // 拿到对象后 通过反射执行 init 方法，获得结果对象
        try {
            Method initMethod = obj.getClass().getMethod("init", DecisionEntity.class, Byte.class);
            Object invoke = initMethod.invoke(obj, entity, Byte.parseByte(String.valueOf(isSimulation)));
            return (DecisionWarnEntity) invoke;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * 执行方法
     *
     * @param strategyEnum
     * @return
     */
    @Override
    public DecisionWarnEntity execute(Enum strategyEnum, List<DecisionWarnEntity> list, DecisionEntity entity) {
        // 使用工厂，创建出需要的对象实例
        Object obj = DecisionStrategyFactory.getInstance().execute(strategyEnum);

        // 拿到对象后 通过反射执行 init 方法，获得结果对象
        try {
            Method initMethod = obj.getClass().getMethod(INIT_METHOD_NAME, List.class, DecisionEntity.class);
            Object invoke = initMethod.invoke(obj, list, entity);
            return (DecisionWarnEntity) invoke;
        } catch (Exception e) {
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
    public static DecisionWarnEntity get(DecisionStrategyEnum strategyEnum, DecisionEntity entity, Byte isSimulation) {
        return instance.execute(strategyEnum, entity, isSimulation);
    }

    /**
     * 获得综合概况结果
     *
     * @param strategyEnum
     * @return
     */
    public static DecisionWarnEntity get(DecisionStrategyEnum strategyEnum, List<DecisionWarnEntity> list, DecisionEntity entity) {
        return instance.execute(strategyEnum, list, entity);
    }

}
