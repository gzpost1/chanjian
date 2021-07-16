package com.yjtech.wisdom.tourism.systemconfig.simulation.factory.task;

import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.SimulationQueryDto;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.praise.SimulationPraiseDto;
import com.yjtech.wisdom.tourism.systemconfig.simulation.enums.SimulationEnum;
import com.yjtech.wisdom.tourism.systemconfig.simulation.service.SimulationConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author 李波
 * @description: 口碑
 * @date 2021/7/1412:07
 */
@Slf4j
@Component(value = SimulationConstants.PRAISE + "_task")
public class SimulationPraiseTask {
    @Autowired
    private SimulationConfigService service;
    /**
     * 每小时修改随机数并重新计算
     */
//    @Scheduled(cron = "0/1 * * * * ? ")
    public void generateRandom() {
        log.info("口碑配置随机数修改开始-------------start");
        SimulationQueryDto simulationQueryDto = new SimulationQueryDto();
        simulationQueryDto.setDomainId(SimulationEnum.PRAISE.getType());

        SimulationPraiseDto dto = (SimulationPraiseDto) service.queryForDetail(simulationQueryDto);
        log.info("口碑配置随机数,获取配置为："+dto);
        if (Objects.nonNull(dto)) {
            //随机数
            service.generateRandom(dto);
            service.saveUpdated(dto);
        }

        log.info("口碑配置随机数修改结束-------------end");
    }


}
