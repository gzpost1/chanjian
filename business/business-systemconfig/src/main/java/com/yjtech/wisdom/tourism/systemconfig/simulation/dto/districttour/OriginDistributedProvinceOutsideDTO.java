package com.yjtech.wisdom.tourism.systemconfig.simulation.dto.districttour;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 来源分布
 *
 * @author renguangqian
 * @date 2021/8/12 11:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OriginDistributedProvinceOutsideDTO implements Serializable {

    private static final long serialVersionUID = 2206367471590836815L;

    /**
     * 区域名称
     */
    private String name;

    /**
     * 比例 %
     */
    private String value;

}
