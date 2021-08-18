package com.yjtech.wisdom.tourism.resource.scenic.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 趋势参数
 */
@Data
public class ScenicTrendDto {

    //当前开始时间
    private LocalDateTime curBeginDate;
    //当前结束时间
    private LocalDateTime curEndDate;
    //同比开始时间
    private LocalDateTime tbBeginDate;
    //同比结束时间
    private LocalDateTime tbEndDate;
    //环比开始时间
    private LocalDateTime hbBeginDate;
    //环比结束时间
    private LocalDateTime hbEndDate;
    //截至当前时间集合
    List<String> abscissa;


}
