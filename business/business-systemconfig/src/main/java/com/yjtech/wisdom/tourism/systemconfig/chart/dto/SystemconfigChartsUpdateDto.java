package com.yjtech.wisdom.tourism.systemconfig.chart.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 系统配置-图标库
 */
@Data
public class SystemconfigChartsUpdateDto extends SystemconfigChartsCreateDto {
    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;
}