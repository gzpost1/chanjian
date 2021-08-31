package com.yjtech.wisdom.tourism.resource.scenic.extensionpoint;

import com.yjtech.wisdom.tourism.common.bean.index.ScenicBuildingDTO;
import com.yjtech.wisdom.tourism.common.bean.index.TodayRealTimeStatisticsDTO;
import com.yjtech.wisdom.tourism.extension.ExtensionPointI;
import com.yjtech.wisdom.tourism.mybatis.entity.IndexQueryVO;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketSummaryQuery;

/**
 *  综合总览  景区数据
 * @author xulei
 * @create 2021-07-14 14:52
 */
public interface IndexScenicQryExtPt extends ExtensionPointI {

    /**
     * 今日接待人次 景区承载量
     */
    TodayRealTimeStatisticsDTO queryVisitStatistics(TicketSummaryQuery query);

    /**
     * 景区建设
     */
    ScenicBuildingDTO scenicBuilding(IndexQueryVO vo);

}
