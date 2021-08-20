package com.yjtech.wisdom.tourism.vo;

import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.mybatis.entity.TimeBaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 游客总数居-数据总览
 *
 * @author renguangqian
 * @date 2021/7/22 15:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataOverviewVo extends TimeBaseQuery implements Serializable {

    private static final long serialVersionUID = 3568648722852543396L;

    /**
     * 统计类型:10.到访全部游客(默认),11.到访省内游客 12.到访省外游客 20.出访全部游客 21.出访省内游客 22.出访省外游客
     */
    private String statisticsType;

    /**
     * 区域编号
     */
    private String adcode;

    /**
     * is_simulation 是否有模拟数据 1有 0无
     */
    private Integer isSimulation = 0;

    /**
     * 开始时间
     */
    private String beginDate;

    /**
     * 结束时间
     */
    private String endDate;
}
