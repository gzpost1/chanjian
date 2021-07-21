package com.yjtech.wisdom.tourism.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分布基础信息 DTO
 *
 * @Author horadirm
 * @Date 2020/11/6 11:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributionBaseInfo implements Serializable {

    private static final long serialVersionUID = 6607510528978059701L;

    /**
     * 信息名称
     */
    private String name;

    /**
     * 信息数值
     */
    private Integer number;

    /**
     * 信息占比
     */
    private String percent;
}
