package com.yjtech.wisdom.tourism.marketing.extensionpoint.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.marketing.extensionpoint.HotelExtensionConstant;
import com.yjtech.wisdom.tourism.marketing.extensionpoint.HotelQryExtPt;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.EvaluateSatisfactionRankDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.RoomPriceAnalysisDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.RoomTypePriceScreenDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateQueryVO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.RoomScreenQueryVO;
import com.yjtech.wisdom.tourism.marketing.service.MarketingEvaluateService;
import com.yjtech.wisdom.tourism.marketing.service.MarketingHotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 酒店民宿评价 真实数据 扩展
 *
 * @author horadirm
 * @date 2021/8/21 10:44
 */
@Extension(bizId = ExtensionConstant.HOTEL,
        useCase = HotelExtensionConstant.HOTEL_QUANTITY,
        scenario = ExtensionConstant.SCENARIO_IMPL)
public class ImplHotelQryExtPt implements HotelQryExtPt {

    @Autowired
    private MarketingEvaluateService marketingEvaluateService;
    @Autowired
    private MarketingHotelRoomService marketingHotelRoomService;

    /**
     * 查询评价统计-酒店民宿详情
     *
     * @param vo
     * @return
     */
    @Override
    public MarketingEvaluateStatisticsDTO queryEvaluateStatistics(EvaluateQueryVO vo) {
        return marketingEvaluateService.queryEvaluateStatistics(vo);
    }

    /**
     * 查询评价统计-酒店民宿大数据
     *
     * @param vo
     * @return
     */
    @Override
    public MarketingEvaluateStatisticsDTO queryEvaluateStatisticsBigData(EvaluateQueryVO vo) {
        return marketingEvaluateService.queryEvaluateStatistics(vo);
    }

    /**
     * 查询评价统计-综合总览
     *
     * @param vo
     * @return
     */
    @Override
    public MarketingEvaluateStatisticsDTO queryEvaluateStatisticsIndex(EvaluateQueryVO vo) {
        return marketingEvaluateService.queryEvaluateStatistics(vo);
    }

    /**
     * 查询评价类型分布-酒店民宿详情
     *
     * @param vo
     * @return
     */
    @Override
    public List<BasePercentVO> queryEvaluateTypeDistribution(EvaluateQueryVO vo) {
        return marketingEvaluateService.queryEvaluateTypeDistribution(vo);
    }

    /**
     * 查询评价类型分布-酒店民宿大数据
     * @param vo
     * @return
     */
    @Override
    public List<BasePercentVO> queryEvaluateTypeDistributionBigData(EvaluateQueryVO vo) {
        return marketingEvaluateService.queryEvaluateTypeDistribution(vo);
    }

    /**
     * 查询评价热词排行-酒店民宿详情
     *
     * @param vo
     * @return
     */
    @Override
    public List<BaseVO> queryEvaluateHotRank(EvaluateQueryVO vo) {
        return marketingEvaluateService.queryEvaluateHotRank(vo);
    }

    /**
     * 查询评价热词排行-酒店民宿大数据
     * @param vo
     * @return
     */
    @Override
    public List<BaseVO> queryEvaluateHotRankBigData(EvaluateQueryVO vo) {
        return marketingEvaluateService.queryEvaluateHotRank(vo);
    }

    /**
     * 查询房型价格统计-酒店民宿详情
     *
     * @param vo
     * @return
     */
    @Override
    public RoomTypePriceScreenDTO queryRoomPriceStatistics(RoomScreenQueryVO vo) {
        return marketingHotelRoomService.queryRoomPriceStatistics(vo);
    }

    /**
     * 查询房型价格统计-酒店民宿详情
     *
     * @param vo
     * @return
     */
    @Override
    public RoomTypePriceScreenDTO queryRoomPriceStatisticsBigData(RoomScreenQueryVO vo) {
        return marketingHotelRoomService.queryRoomPriceStatistics(vo);
    }

    /**
     * 查询房型价格趋势-酒店民宿详情
     *
     * @param vo
     * @return
     */
    @Override
    public List<RoomPriceAnalysisDTO> queryRoomPriceAnalysis(RoomScreenQueryVO vo) {
        return marketingHotelRoomService.queryRoomPriceAnalysis(vo);
    }

    /**
     * 查询房型价格趋势-酒店民宿大数据
     * @param vo
     * @return
     */
    @Override
    public List<RoomPriceAnalysisDTO> queryRoomPriceAnalysisBigData(RoomScreenQueryVO vo) {
        return marketingHotelRoomService.queryRoomPriceAnalysis(vo);
    }

    /**
     * 查询酒店评价排行
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<BaseVO> queryEvaluateRank(EvaluateQueryVO vo) {
        return marketingEvaluateService.queryEvaluateRank(vo);
    }

    /**
     * 查询酒店满意度排行
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<EvaluateSatisfactionRankDTO> queryEvaluateSatisfactionRank(EvaluateQueryVO vo) {
        return marketingEvaluateService.queryEvaluateSatisfactionRank(vo);
    }

    /**
     * 查询评价量趋势、同比、环比
     *
     * @param vo
     * @return
     */
    @Override
    public List<AnalysisBaseInfo> queryEvaluateAnalysis(EvaluateQueryVO vo) {
        return marketingEvaluateService.queryEvaluateAnalysis(vo);
    }

    /**
     * 查询评价满意度趋势、同比、环比
     *
     * @param vo
     * @return
     */
    @Override
    public List<AnalysisBaseInfo> queryEvaluateSatisfactionAnalysis(EvaluateQueryVO vo) {
        return marketingEvaluateService.queryEvaluateSatisfactionAnalysis(vo);
    }

}
