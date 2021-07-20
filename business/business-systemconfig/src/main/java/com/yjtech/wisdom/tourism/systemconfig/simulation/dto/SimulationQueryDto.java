package com.yjtech.wisdom.tourism.systemconfig.simulation.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 李波
 * @description: 查询
 * @date 2021/7/1320:45
 */
@Data
public class SimulationQueryDto {
    /**
     * 1票务 2停车场 3wifi 4一码游 5智慧厕所 6口碑 7事件
     */
    @NotNull(message = "查询类型不能为空")
    @Min(value = 1)
    @Max(value = 7)
    private Integer domainId;
}