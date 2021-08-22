package com.yjtech.wisdom.tourism.marketing.extensionpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.extension.ExtensionPointI;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.EvaluateSatisfactionRankDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.RoomPriceAnalysisDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.RoomTypePriceScreenDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateQueryVO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.RoomScreenQueryVO;

import java.util.List;

/**
 * 酒店民宿评价
 *
 * @date 2021/8/21 13:37
 * @author horadirm
 */
public interface HotelQryExtPt extends ExtensionPointI {

    /**
     * 查询评价统计-酒店民宿详情
     *
     * @param vo
     * @return
     */
    MarketingEvaluateStatisticsDTO queryEvaluateStatisticsDetail(EvaluateQueryVO vo);

    /**
     * 查询评价统计-综合总览
     *
     * @param vo
     * @return
     */
    MarketingEvaluateStatisticsDTO queryEvaluateStatisticsIndex(EvaluateQueryVO vo);

    /**
     * 查询评价统计-酒店民宿大数据
     *
     * @param vo
     * @return
     */
    MarketingEvaluateStatisticsDTO queryEvaluateStatisticsBigData(EvaluateQueryVO vo);

    /**
     * 查询评价类型分布
     *
     * @param vo
     * @return
     */
    List<BasePercentVO> queryEvaluateTypeDistribution(EvaluateQueryVO vo);

    /**
     * 查询评价热词排行
     *
     * @param vo
     * @return
     */
    List<BaseVO> queryEvaluateHotRank(EvaluateQueryVO vo);

    /**
     * 查询房型价格统计
     * @param vo
     * @return
     */
    RoomTypePriceScreenDTO queryRoomPriceStatistics(RoomScreenQueryVO vo);

    /**
     * 查询房型价格趋势
     * @param vo
     * @return
     */
    List<RoomPriceAnalysisDTO> queryRoomPriceAnalysis(RoomScreenQueryVO vo);

    /**
     * 查询酒店评价排行
     *
     * @param vo
     * @return
     */
    IPage<BaseVO> queryEvaluateRank(EvaluateQueryVO vo);
    /**
     * 查询酒店满意度排行
     *
     * @param vo
     * @return
     */
    IPage<EvaluateSatisfactionRankDTO> queryEvaluateSatisfactionRank(EvaluateQueryVO vo);

    /**
     * 查询景区评价排行
     *
     * @return
     */
    IPage<BaseVO> queryEvaluateTop5(EvaluateQueryVO query);

    /**
     * 查询景区满意度排行
     *
     * @return
     */
    IPage<EvaluateSatisfactionRankDTO> querySatisfactionTop5(EvaluateQueryVO query);


    /**
     * 查询评价量趋势、同比、环比
     *
     * @return
     */
    List<AnalysisBaseInfo> queryEvaluateAnalysis(EvaluateQueryVO vo);

    /**
     * 查询评价满意度趋势、同比、环比
     *
     * @return
     */
    List<AnalysisBaseInfo> queryEvaluateSatisfactionAnalysis(EvaluateQueryVO vo);

}
