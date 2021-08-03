package com.yjtech.wisdom.tourism.vo;

import com.yjtech.wisdom.tourism.common.bean.TimeBaseQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 客流
 *
 * @author renguangqian
 * @date 2021/7/29 19:18
 */
@Data
@Builder
@AllArgsConstructor
public class PassengerFlowVo extends TimeBaseQuery implements Serializable {

    private static final long serialVersionUID = -8241097236142759430L;

    /**
     * 统计类型:10.到访全部游客(默认),11.到访省内游客 12.到访省外游客 20.出访全部游客 21.出访省内游客 22.出访省外游客
     */
    private String statisticsType;

    /**
     * 区域编号
     */
    private String adcode;

    /**
     * 开始日期 - 不用传,使用beginTime
     */
    private String beginDate;

    /**
     * 结束日期- 不用传,使用endTime
     */
    private String endDate;
}
