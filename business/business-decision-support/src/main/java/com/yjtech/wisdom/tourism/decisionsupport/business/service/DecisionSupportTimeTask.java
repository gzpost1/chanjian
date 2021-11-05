package com.yjtech.wisdom.tourism.decisionsupport.business.service;

import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.decisionsupport.business.vo.AnalyzeDecisionWarnVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 决策辅助-每月执行一次的定时任务
 *
 * @author renguangqian
 * @date 2021/8/31 13:45
 */
@Component("decisionSupportTimeTask")
@Slf4j
public class DecisionSupportTimeTask {

    @Autowired
    private DecisionSupportScreenService decisionSupportScreenService;

    /**
     * 分析辅助指标
     */
    public void analysisTask(Integer isSimulation) {
        log.info("==================> 【决策辅助-指标分析】定时任务 <==================");
        log.info("执行开始时间：{}", DateTimeUtil.getCurrentTime());
        AnalyzeDecisionWarnVo analyzeDecisionWarnVo = new AnalyzeDecisionWarnVo();
        if (null != isSimulation) {
            analyzeDecisionWarnVo.setIsSimulation(isSimulation);
        }
        decisionSupportScreenService.analyzeDecisionWarn(analyzeDecisionWarnVo);
        log.info("执行完成时间：{}", DateTimeUtil.getCurrentTime());
    }
}
