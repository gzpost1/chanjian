package com.yjtech.wisdom.tourism.systemconfig.simulation.dto.decisionsupport;

import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.SimulationCommonDto;
import lombok.Data;

import java.util.List;

/**
 * 模拟数据 - 决策辅助
 *
 * @author renguangqian
 * @date 2021/8/23 17:16
 */
@Data
public class SimulationDecisionSupportDTO extends SimulationCommonDto{

    /**
     * 模拟数据
     */
    private List<DecisionMockDTO> list;
}
