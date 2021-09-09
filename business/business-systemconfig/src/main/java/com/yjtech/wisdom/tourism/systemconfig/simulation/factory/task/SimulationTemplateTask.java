package com.yjtech.wisdom.tourism.systemconfig.simulation.factory.task;

import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.SimulationQueryDto;
import com.yjtech.wisdom.tourism.systemconfig.simulation.service.SimulationConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 模拟数据模板刷新
 *
 * @date 2021/9/9 10:34
 * @author horadirm
 */
@Component("SimulationTemplateTask")
@Slf4j
public class SimulationTemplateTask {

    @Autowired
    private SimulationConfigService simulationConfigService;

    /**
     * 一码游模板数据刷新
     */
    public void refreshOneTravelSimulationTemplate() {
        log.info("******************** {}刷新一码游模拟数据模板信息 ********************", new Date());
        simulationConfigService.refreshData(new SimulationQueryDto(4));
        log.info("********************  一码游模拟数据模板信息每日刷新完成 ********************");
    }

    /**
     * 旅游投诉模板数据刷新
     */
    public void refreshTravelComplaintSimulationTemplate() {
        log.info("******************** {}刷新旅游投诉模拟数据模板信息 ********************", new Date());
        simulationConfigService.refreshData(new SimulationQueryDto(10));
        log.info("********************  旅游投诉模拟数据模板信息每日刷新完成 ********************");
    }

    /**
     * 酒店民宿模板数据刷新
     */
    public void refreshHotelSimulationTemplate() {
        log.info("******************** {}刷新酒店民宿模拟数据模板信息 ********************", new Date());
        simulationConfigService.refreshData(new SimulationQueryDto(9));
        log.info("********************  酒店民宿模拟数据模板信息每日刷新完成 ********************");
    }


}
