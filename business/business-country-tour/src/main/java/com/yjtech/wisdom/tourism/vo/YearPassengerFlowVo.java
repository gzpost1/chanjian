package com.yjtech.wisdom.tourism.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 本年客流趋势
 *
 * @author renguangqian
 * @date 2021/7/22 19:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class YearPassengerFlowVo implements Serializable {

    private static final long serialVersionUID = 8096805950615093800L;

    /**
     * 省市区域编号
     */
    private String adcode;

    /**
     * 统计到访还是出访:0.到访(默认) 1.出访
     */
    private String statisticsType;

    /**
     * 趋势所在年月,默认根据当前时间,格式yyyy-MM-dd
     */
    private String date;
}
