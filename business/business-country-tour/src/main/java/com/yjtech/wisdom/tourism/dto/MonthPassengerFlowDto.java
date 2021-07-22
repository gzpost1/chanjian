package com.yjtech.wisdom.tourism.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 月客流趋势
 *
 * @author renguangqian
 * @date 2021/7/22 19:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MonthPassengerFlowDto implements Serializable {

    private static final long serialVersionUID = 5819501213621321635L;

    /**
     * 时间
     */
    private String date;

    /**
     * 数量
     */
    private Integer number;

    /**
     * 前一日
     */
    private String beforeDay;

    /**
     * 去年今天
     */
    private String lastYearDay;
}
