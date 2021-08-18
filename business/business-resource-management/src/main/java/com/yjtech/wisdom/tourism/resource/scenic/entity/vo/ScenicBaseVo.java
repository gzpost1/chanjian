package com.yjtech.wisdom.tourism.resource.scenic.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**景区公共vo*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ScenicBaseVo {

    /**
     * id
     */
    private String scenicId;

    /**名称*/
    private String name;

    /**值*/
    private String value;
}
