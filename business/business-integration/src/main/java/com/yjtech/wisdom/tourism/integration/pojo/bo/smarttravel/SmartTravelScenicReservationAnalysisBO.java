package com.yjtech.wisdom.tourism.integration.pojo.bo.smarttravel;

import com.yjtech.wisdom.tourism.common.bean.AnalysisChartBaseInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 景区中心 景区预约趋势 BO
 *
 * @Author horadirm
 * @Date 2021/5/26 15:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmartTravelScenicReservationAnalysisBO extends AnalysisChartBaseInfo {

    private static final long serialVersionUID = 6235634146354227348L;

    /**
     * 预约信息
     */
    private Integer currentInfo;

    public SmartTravelScenicReservationAnalysisBO(String time) {
        super(time);
        this.currentInfo = 0;
    }

}
