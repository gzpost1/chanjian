package com.yjtech.wisdom.tourism.command.extensionpoint;

import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintScreenQueryVO;
import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.extension.ExtensionPointI;

import java.util.List;

/**
 * 旅游投诉
 *
 * @date 2021/8/21 10:17
 * @author horadirm
 */
public interface TravelComplaintQryExtPt extends ExtensionPointI {

    /**
     * 查询综合总览-旅游投诉总量
     *
     * @param vo
     * @return
     */
    Integer queryTravelComplaintTotalToIndex(TravelComplaintScreenQueryVO vo);

    /**
     * 查询旅游投诉总量
     *
     * @param vo
     * @return
     */
    Integer queryTravelComplaintTotal(TravelComplaintScreenQueryVO vo);

    /**
     * 查询旅游投诉类型分布
     *
     * @param vo
     * @return
     */
    List<BasePercentVO> queryComplaintTypeDistribution(TravelComplaintScreenQueryVO vo);

    /**
     * 查询旅游投诉状态分布
     *
     * @param vo
     * @return
     */
    List<BasePercentVO> queryComplaintStatusDistribution(TravelComplaintScreenQueryVO vo);

    /**
     * 查询旅游投诉类型Top排行
     *
     * @param vo
     * @return
     */
    List<BaseVO> queryComplaintTopByType(TravelComplaintScreenQueryVO vo);

    /**
     * 查询旅游投诉趋势、同比、环比
     *
     * @return
     */
    List<AnalysisBaseInfo> queryComplaintAnalysis(TravelComplaintScreenQueryVO vo);

}
