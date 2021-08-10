package com.yjtech.wisdom.tourism.decisionsupport.base.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 根据时间范围获取省内或省外或全国的到访或出访数据
 *
 * @author renguangqian
 * @date 2021/7/28 9:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class VisitNumberVo implements Serializable {

    private static final long serialVersionUID = -537792113619976605L;

    /**
     * 统计类型:10.到访全部游客(默认),11.到访省内游客 12.到访省外游客 20.出访全部游客 21.出访省内游客 22.出访省外游客
     */
    private String statisticsType;

    /**
     * 起始日期yyyy-MM-dd,默认当天
     */
    private String beginDate ;

    /**
     * 结束日期yyyy-MM-dd,默认当天
     */
    private String endDate ;

    /**
     * 区域编号
     */
    private String adcode;

}
