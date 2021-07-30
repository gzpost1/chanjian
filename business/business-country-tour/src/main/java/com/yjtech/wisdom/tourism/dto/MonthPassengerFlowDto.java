package com.yjtech.wisdom.tourism.dto;

import com.yjtech.wisdom.tourism.common.bean.BaseSaleTrendVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
    private Integer tbNumber;

    /**
     * 同比日期
     */
    private String tbDate;

    /**
     * 前一日
     */
    private String hbScale;

    /**
     * 去年今天
     */
    private String tbScale;
}
