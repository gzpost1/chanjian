package com.yjtech.wisdom.tourism.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 省内来源分布
 *
 * @author renguangqian
 * @date 2021/8/13 9:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OriginDistributedProvinceInsideDto implements Serializable {

    private static final long serialVersionUID = 2966992910895335977L;

    /**
     * 区域名称
     */
    private String name;

    /**
     * 比例 %
     */
    private double value;
}
