package com.yjtech.wisdom.tourism.common.bean.index;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 景区建设
 *
 * @date 2021/8/19 10:57
 * @author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScenicBuildingDTO implements Serializable {

    private static final long serialVersionUID = 450710425213277568L;

    /**
     * 接待游客人次
     */
    private Long tourist;

    /**
     * 收获评价（条）
     */
    private Integer evaluate;

    /**
     * 满意度
     */
    private BigDecimal satisfaction;

}
