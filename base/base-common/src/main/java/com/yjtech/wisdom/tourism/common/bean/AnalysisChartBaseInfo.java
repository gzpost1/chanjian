package com.yjtech.wisdom.tourism.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 趋势图表基础信息
 *
 * @Author horadirm
 * @Date 2021/5/10 15:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisChartBaseInfo implements Serializable {

    private static final long serialVersionUID = -1536971811933589309L;
    /**
     * 日期
     */
    private String time;

}
