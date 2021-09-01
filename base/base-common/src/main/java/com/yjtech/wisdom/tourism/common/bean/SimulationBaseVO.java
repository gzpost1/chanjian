package com.yjtech.wisdom.tourism.common.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 模拟数据 基础VO
 *
 * @date 2021/9/1 11:51
 * @author horadirm
 */
@Data
public class SimulationBaseVO implements Serializable {

    private static final long serialVersionUID = 5287769806205956927L;

    /**
     * is_simulation 是否有模拟数据 1有 0无
     */
    private Byte isSimulation = 0;

}
