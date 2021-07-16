package com.yjtech.wisdom.tourism.resource.depot.entity.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @Description
 * @Author 陈忠凌
 * @Date 2020-09-15 14:00
 */
@Getter
@Setter
public class UpdateDepotDto extends CreateDepotDto {

    @NotNull(message = "停车场id不能为空")
    private Long depotId;
}
