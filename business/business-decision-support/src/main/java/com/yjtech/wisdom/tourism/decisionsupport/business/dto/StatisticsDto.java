package com.yjtech.wisdom.tourism.decisionsupport.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 满意度
 *
 * @author renguangqian
 * @date 2021/8/18 14:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StatisticsDto implements Serializable {
    /**
     * 总数
     */
    private Integer total;

    /**
     * 好评总数
     */
    private Integer goodTotal;

    /**
     * 满意度
     */
    private String satisfaction;
}
