package com.yjtech.wisdom.tourism.resource.depot.entity.vo;

import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**大屏-停车场使用率vo*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DepotScreenUseVo {

    /**总体使用率*/
    private Double totalPercent;

    /**总停车位*/
    private Integer totalSpace;

    /**已停车位*/
    private Integer useSpace;

    /**剩余停车位*/
    private Integer surplusSpace;

    /**当日车辆出入趋势*/
    private List<BaseValueVO> trend;
}
