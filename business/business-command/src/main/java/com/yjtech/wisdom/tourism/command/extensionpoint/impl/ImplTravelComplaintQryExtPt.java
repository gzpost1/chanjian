package com.yjtech.wisdom.tourism.command.extensionpoint.impl;

import com.yjtech.wisdom.tourism.command.extensionpoint.TravelComplaintExtensionConstant;
import com.yjtech.wisdom.tourism.command.extensionpoint.TravelComplaintQryExtPt;
import com.yjtech.wisdom.tourism.command.service.travelcomplaint.TravelComplaintService;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintScreenQueryVO;
import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 旅游投诉 真实数据 扩展
 *
 * @date 2021/8/21 10:44
 * @author horadirm
 */
@Extension(bizId = ExtensionConstant.TRAVEL_COMPLAINT,
        useCase = TravelComplaintExtensionConstant.TRAVEL_COMPLAINT_QUANTITY,
        scenario = ExtensionConstant.SCENARIO_IMPL)
public class ImplTravelComplaintQryExtPt implements TravelComplaintQryExtPt {


    @Autowired
    private TravelComplaintService travelComplaintService;


    /**
     * 查询综合总览-旅游投诉总量
     * @param vo
     * @return
     */
    @Override
    public Integer queryTravelComplaintTotalToIndex(TravelComplaintScreenQueryVO vo) {
        return travelComplaintService.queryTravelComplaintTotalToIndex(vo);
    }

    /**
     * 查询旅游投诉总量
     * @param vo
     * @return
     */
    @Override
    public Integer queryTravelComplaintTotal(TravelComplaintScreenQueryVO vo) {
        return travelComplaintService.queryTravelComplaintTotal(vo);
    }

    /**
     * 查询旅游投诉类型分布
     * @param vo
     * @return
     */
    @Override
    public List<BasePercentVO> queryComplaintTypeDistribution(TravelComplaintScreenQueryVO vo) {
        return travelComplaintService.queryComplaintTypeDistribution(vo);
    }

    /**
     * 查询旅游投诉状态分布
     * @param vo
     * @return
     */
    @Override
    public List<BasePercentVO> queryComplaintStatusDistribution(TravelComplaintScreenQueryVO vo) {
        return travelComplaintService.queryComplaintStatusDistribution(vo);
    }

    /**
     * 查询旅游投诉类型Top排行
     * @param vo
     * @return
     */
    @Override
    public List<BaseVO> queryComplaintTopByType(TravelComplaintScreenQueryVO vo) {
        return travelComplaintService.queryComplaintTopByType(vo);
    }

    /**
     * 查询旅游投诉趋势、同比、环比
     * @param vo
     * @return
     */
    @Override
    public List<AnalysisBaseInfo> queryComplaintAnalysis(TravelComplaintScreenQueryVO vo) {
        return travelComplaintService.queryComplaintAnalysis(vo);
    }

}
