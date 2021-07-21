package com.yjtech.wisdom.tourism.integration.pojo.bo.fxdist;

import com.yjtech.wisdom.tourism.common.bean.AnalysisChartBaseInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 珊瑚礁 销售趋势 BO
 *
 * @Author horadirm
 * @Date 2021/5/28 9:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FxDistSaleAnalysisBO extends AnalysisChartBaseInfo {

    private static final long serialVersionUID = 6235634146354227348L;

    /**
     * 销售额
     */
    private BigDecimal currentInfo;

    public FxDistSaleAnalysisBO(String time) {
        super(time);
        this.currentInfo = BigDecimal.ZERO;
    }


}
