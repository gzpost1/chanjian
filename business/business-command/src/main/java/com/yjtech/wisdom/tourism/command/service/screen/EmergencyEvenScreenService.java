package com.yjtech.wisdom.tourism.command.service.screen;

import com.yjtech.wisdom.tourism.command.extensionpoint.EventExtensionConstant;
import com.yjtech.wisdom.tourism.command.extensionpoint.EventQryExtPt;
import com.yjtech.wisdom.tourism.command.query.event.EventSumaryQuery;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.extension.BizScenario;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.extension.ExtensionExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 应急事件  大屏
 *
 * @author renguangqian
 * @date 2021/8/10 15:06
 */
@Service
public class EmergencyEvenScreenService {

    @Resource
    private ExtensionExecutor extensionExecutor;

    /**
     * 应急事件统计（首页及事件页面）
     *
     * @param query
     * @return
     */
    public List<BaseVO> queryEventQuantity(EventSumaryQuery query) {
        return extensionExecutor.execute(EventQryExtPt.class,
                buildBizScenario(EventExtensionConstant.EVENT_QUANTITY, query.getIsSimulation()),
                extension -> extension.queryEventQuantity(query));
    }


    /**
     * 事件发生趋势
     *
     * @param query
     * @return
     */
    public List<BaseValueVO> querySaleTrend(EventSumaryQuery query) {
        return extensionExecutor.execute(EventQryExtPt.class,
                buildBizScenario(EventExtensionConstant.EVENT_QUANTITY, query.getIsSimulation()),
                extension -> extension.querySaleTrend(query));
    }

    /**
     * 事件类型分布
     *
     * @param query
     * @return
     */
    public List<BaseVO> queryEventType(EventSumaryQuery query) {
        return extensionExecutor.execute(EventQryExtPt.class,
                buildBizScenario(EventExtensionConstant.EVENT_QUANTITY, query.getIsSimulation()),
                extension -> extension.queryEventType(query));
    }

    /**
     * 事件级别分布
     *
     * @param query
     * @return
     */
    public List<BaseVO> queryEventLevel(EventSumaryQuery query) {
        return extensionExecutor.execute(EventQryExtPt.class,
                buildBizScenario(EventExtensionConstant.EVENT_QUANTITY, query.getIsSimulation()),
                extension -> extension.queryEventLevel(query));
    }

    /**
     * 构建
     *
     * @param useCasePraiseType
     * @param isSimulation
     * @return
     */
    private BizScenario buildBizScenario(String useCasePraiseType, Integer isSimulation) {
        return BizScenario.valueOf(ExtensionConstant.BIZ_EVENT, useCasePraiseType
                , isSimulation == 0 ? ExtensionConstant.SCENARIO_IMPL : ExtensionConstant.SCENARIO_MOCK);
    }
}
