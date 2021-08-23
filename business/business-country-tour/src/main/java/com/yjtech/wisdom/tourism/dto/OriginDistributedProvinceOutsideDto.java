package com.yjtech.wisdom.tourism.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

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
public class OriginDistributedProvinceOutsideDto implements Serializable {

    private static final long serialVersionUID = 2206367471590836815L;

    /**
     * 区域名称
     */
    private String name;

    /**
     * 比例 %
     */
    private double value;

}
