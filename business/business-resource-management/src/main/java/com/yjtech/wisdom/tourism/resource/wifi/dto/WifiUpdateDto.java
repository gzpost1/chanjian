package com.yjtech.wisdom.tourism.resource.wifi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 资源管理-WiFi设备
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WifiUpdateDto extends WifiCreateDto {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;
}