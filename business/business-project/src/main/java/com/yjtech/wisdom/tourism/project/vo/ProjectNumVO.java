package com.yjtech.wisdom.tourism.project.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * description: ProjectNumVO
 * date: 2022/7/11 15:07
 * author: chenzhongge
 */
@Data
public class ProjectNumVO {

    private String type;

    private Integer projectNum;

    private BigDecimal investmentTotal;
}
