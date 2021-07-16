package com.yjtech.wisdom.tourism.resource.location.vo;

import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description
 * @Author Mujun~
 * @Date 2020-10-12 14:48
 */
@Data
public class StaticsNumVo {
    private Integer total;
    private String type;
    private Integer onlineNum ;
    private Integer offlineNum;
    private BigDecimal onlinePercent;
    private BigDecimal offlinePercent;

    public BigDecimal getOnlinePercent() {
        return MathUtil.divide(BigDecimal.valueOf(onlineNum),BigDecimal.valueOf(total));
    }

    public BigDecimal getOfflinePercent() {
        return MathUtil.divide(BigDecimal.valueOf(offlineNum),BigDecimal.valueOf(total));
    }
}
