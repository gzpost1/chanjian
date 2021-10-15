package com.yjtech.wisdom.tourism.dto;

import com.yjtech.wisdom.tourism.common.bean.BaseSaleTrendVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class MonthPassengerFlowDto extends BaseSaleTrendVO implements Serializable {

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
     * 同比数量
     */
    private int tbNumber;

    /**
     * 当前月满意度
     */
    private BigDecimal rate;

    /**
     * 同步月满意度
     */
    private BigDecimal tbRate;

    /**
     * 同比日期
     */
    private String tbDate;

    /**
     * 环比比例
     */
    private String hbScale;

    /**
     * 同比比例
     */
    private String tbScale;
}
