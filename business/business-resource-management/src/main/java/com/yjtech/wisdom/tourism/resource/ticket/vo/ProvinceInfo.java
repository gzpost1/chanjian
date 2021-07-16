package com.yjtech.wisdom.tourism.resource.ticket.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liuhong
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceInfo {
    /* 省名 */
    private String province;

    /*数量*/
    private int quantity;

    /* 各地市信息 */
    private List<CityInfo> cityInfo;
}
