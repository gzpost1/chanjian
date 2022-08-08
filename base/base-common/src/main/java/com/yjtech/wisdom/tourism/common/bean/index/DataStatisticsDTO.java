package com.yjtech.wisdom.tourism.common.bean.index;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 数据统计 DTO
 *
 * @date 2022/8/5 16:51
 * @author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataStatisticsDTO implements Serializable {
    private static final long serialVersionUID = 1582793533633979280L;

    /**
     * 总数量
     */
    private Integer totalNum;

    /**
     * 当月数量
     */
    private Integer currentMonthNum;

}
