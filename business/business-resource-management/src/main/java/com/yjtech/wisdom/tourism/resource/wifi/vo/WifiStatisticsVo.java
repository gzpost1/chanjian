package com.yjtech.wisdom.tourism.resource.wifi.vo;

import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class WifiStatisticsVo {

    /**当前连接数*/
    private Integer currentConnectNum;

    /**连接占比*/
    private Double connectRate;

    /**未连接占比*/
    private Double notConnectRate;

    /**top10数据*/
    private List<BaseValueVO> top10List;
}
