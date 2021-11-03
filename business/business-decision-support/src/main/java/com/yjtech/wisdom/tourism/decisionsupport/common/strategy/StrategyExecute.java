package com.yjtech.wisdom.tourism.decisionsupport.common.strategy;


import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnEntity;

import java.util.List;

/**
 * 策略执行器
 *
 * @author renguangqian
 * @date 2021/8/5 14:55
 */
public abstract class StrategyExecute {

    /**
     * 执行方法
     *
     * @param e
     * @return
     */
   public Object execute(Enum e){return null;}

    /**
     * 执行方法
     *
     * @param e
     * @return
     */
    public Object execute(Enum e, DecisionEntity o, Byte isSimulation){return null;}

    /**
     * 执行方法
     *
     * @param e
     * @return
     */
    public Object execute(Enum e, List<DecisionWarnEntity> o, DecisionEntity entity){return null;}
}
