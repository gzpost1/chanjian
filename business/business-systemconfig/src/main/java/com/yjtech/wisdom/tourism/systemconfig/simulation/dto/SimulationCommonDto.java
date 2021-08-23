package com.yjtech.wisdom.tourism.systemconfig.simulation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author 李波
 * @description: 模拟数据公共配置
 * @date 2021/7/1320:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimulationCommonDto {
    /**
     * 随机数
     */
    private String randomNumber;

    /**
     * 1票务 2停车场 3wifi 4一码游 5智慧厕所 6口碑 7事件
     * 8景区 9酒店民宿 10旅游投诉 11游客结构 12决策辅助
     */
    @NotBlank(message = "配置类型不能为空")
    @Min(value = 1)
    @Max(value = 12)
    private Integer domainId;
}
