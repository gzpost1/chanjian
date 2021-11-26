package com.yjtech.wisdom.tourism.resource.scenic.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**景区公共vo*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScenicBaseVo {

    /**
     * id
     */
    private Long id;

    /**名称*/
    private String name;

    /**值*/
    private String value;

    /**
     * 百分比
     */
    private Double percent;
}
