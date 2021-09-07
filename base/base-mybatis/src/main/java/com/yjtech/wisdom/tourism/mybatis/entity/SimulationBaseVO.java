package com.yjtech.wisdom.tourism.mybatis.entity;

import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import lombok.Data;

/**
 * 模拟数据 基础VO
 *
 * @date 2021/9/1 11:51
 * @author horadirm
 */
@Data
public class SimulationBaseVO extends TimeBaseQuery {

    private static final long serialVersionUID = 5287769806205956927L;

    /**
     * is_simulation 是否有模拟数据 1有 0无
     */
    private Byte isSimulation = EntityConstants.DISABLED;

}
