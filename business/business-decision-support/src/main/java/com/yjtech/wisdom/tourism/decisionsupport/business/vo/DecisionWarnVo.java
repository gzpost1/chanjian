package com.yjtech.wisdom.tourism.decisionsupport.business.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 决策预警查询
 *
 * @author renguangqian
 * @date 2021/7/29 10:04
 */
@Data
public class DecisionWarnVo implements Serializable {

    private static final long serialVersionUID = -6491336512086877569L;

    /**
     * is_simulation 是否有模拟数据 1有 0无
     */
    private Integer isSimulation = 0;
}
