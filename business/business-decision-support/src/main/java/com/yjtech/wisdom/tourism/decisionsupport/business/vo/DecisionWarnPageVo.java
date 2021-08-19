package com.yjtech.wisdom.tourism.decisionsupport.business.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;

import java.io.Serializable;

/**
 * 决策预警
 *
 * @author renguangqian
 * @date 2021/7/29 8:59
 */
@Data
public class DecisionWarnPageVo extends PageQuery implements Serializable {

    private static final long serialVersionUID = -6491336512086877569L;

    // todo 以后可能会拓展查询条件
    /**
     * is_simulation 是否有模拟数据 1有 0无
     */
    private Integer isSimulation = 0;
}
