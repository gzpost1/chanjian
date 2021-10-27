package com.yjtech.wisdom.tourism.systemconfig.simulation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 李波
 * @description: 查询
 * @date 2021/7/1320:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SimulationQueryDto {
    /**
     * 1票务 2停车场 3wifi 4一码游 5智慧厕所 6口碑 7事件
     * 8景区 9酒店民宿 10旅游投诉 11游客结构 12决策辅助
     */
    @NotNull(message = "查询类型不能为空")
    @Min(value = 1)
    @Max(value = 12)
    private Integer domainId;
}
