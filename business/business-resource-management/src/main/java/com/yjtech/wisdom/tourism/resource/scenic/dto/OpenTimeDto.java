package com.yjtech.wisdom.tourism.resource.scenic.dto;

import lombok.Data;

@Data
public class OpenTimeDto {

    /** 开放日期-开始日期*/
    private String openStartDate;

    /**开放日期-结束日期*/
    private String openEndDate;

    /**开放日期-开始时间*/
    private String openStartTime;

    /**开放日期-结束时间*/
    private String openEndTime;
}
