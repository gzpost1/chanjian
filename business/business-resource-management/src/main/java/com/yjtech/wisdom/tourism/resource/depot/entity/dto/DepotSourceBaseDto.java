package com.yjtech.wisdom.tourism.resource.depot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DepotSourceBaseDto {

    /**时间类型*/
    private Integer timeType;

    /**区域类型 来源,0:省内,1:省外*/
    private Integer sourceType;
}
