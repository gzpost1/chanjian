package com.yjtech.wisdom.tourism.integration.pojo.bo.smarttravel;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: wuyongchong
 * @date: 2020/5/14 19:28
 */
@Data
public class SmartTravelScenicBoundaryInfo implements Serializable {
    private String name;
    private List<SmartTravelScenicPointInfo> data;
}
