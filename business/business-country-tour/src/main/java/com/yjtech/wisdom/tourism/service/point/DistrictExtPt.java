package com.yjtech.wisdom.tourism.service.point;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.dto.DataOverviewDto;
import com.yjtech.wisdom.tourism.dto.MonthPassengerFlowDto;
import com.yjtech.wisdom.tourism.dto.VisitorDto;
import com.yjtech.wisdom.tourism.extension.ExtensionPointI;
import com.yjtech.wisdom.tourism.vo.DataOverviewVo;
import com.yjtech.wisdom.tourism.vo.MonthPassengerFlowVo;
import com.yjtech.wisdom.tourism.vo.PassengerFlowVo;
import com.yjtech.wisdom.tourism.vo.VisitorVo;

import java.util.List;

/**
 * 游客结构拓展点
 *
 * @author renguangqian
 * @date 2021/8/11 19:19
 */
public interface DistrictExtPt extends ExtensionPointI {
    /**
     * 查询游客总数据-数据总览
     *
     * @param vo
     * @return
     */
    DataOverviewDto queryDataOverview (DataOverviewVo vo);

    /**
     * 游客来源_分页查询
     *
     * @param vo
     * @return
     */
    IPage<VisitorDto> queryPageVisitor(VisitorVo vo);

    /**
     * 本年客流趋势
     *
     * @param vo
     * @return
     */
    List<MonthPassengerFlowDto> queryYearPassengerFlow (PassengerFlowVo vo);

    /**
     * 本月客流趋势
     *
     * @param vo
     * @return
     */
    List<MonthPassengerFlowDto> queryMonthPassengerFlow (MonthPassengerFlowVo vo);
}
