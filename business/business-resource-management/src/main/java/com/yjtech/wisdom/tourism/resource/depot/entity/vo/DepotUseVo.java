package com.yjtech.wisdom.tourism.resource.depot.entity.vo;

import com.yjtech.wisdom.tourism.resource.depot.entity.dto.SingleDepotUseDto;
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
public class DepotUseVo {

    /**总体使用率*/
    private Double totalPercent;

    private List<SingleDepotUseDto> depotUseList;
}
