package com.yjtech.wisdom.tourism.systemconfig.simulation.dto.praise;

import lombok.Data;

/**
 * @author 李波
 * @description: 评论热词词频/OTA分布（%）
 * @date 2021/7/1410:54
 */
@Data
public class SimulationPraiseHotWordDto {
    /**
     * OTA名称
     */
    private String name;

    /**
     * 评价占比
     */
    private String rate;

    /**
     * 满意度
     */
    private String satisfaction;
}
