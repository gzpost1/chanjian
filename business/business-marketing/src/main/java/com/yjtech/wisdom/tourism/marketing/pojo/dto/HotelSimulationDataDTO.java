package com.yjtech.wisdom.tourism.marketing.pojo.dto;

import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.index.HotelStatisticsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 酒店民宿 模拟数据
 *
 * @date 2021/8/31 15:15
 * @author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelSimulationDataDTO implements Serializable {

    private static final long serialVersionUID = -6484484372410468935L;

    /**
     * 查询评价统计-酒店民宿详情
     */
    private Map<Long, MarketingEvaluateStatisticsDTO> statisticsDetail;

    /**
     * 查询评价类型分布-酒店民宿详情
     */
    private Map<Long, List<BasePercentVO>> typeDistributionDetail;

    /**
     * 查询评价热词排行-酒店民宿详情
     */
    private Map<Long, List<BaseVO>> hotRankDetail;

    /**
     * 查询房型价格统计-酒店民宿详情
     */
    private Map<Long, RoomTypePriceScreenDTO> roomPriceStatisticsDetail;

    /**
     * 查询房型价格趋势-酒店民宿详情
     */
    private Map<Long, List<RoomPriceAnalysisDTO>> roomPriceAnalysisDetail;

    /**
     * 查询酒店民宿统计-综合总览
     */
    private HotelStatisticsDTO statisticsIndex;

    /**
     * 查询评价统计-酒店民宿大数据
     */
    private MarketingEvaluateStatisticsDTO statisticsBigData;

    /**
     * 查询评价类型分布-酒店民宿大数据
     */
    private List<BasePercentVO> typeDistributionBigData;

    /**
     * 排行总数量-酒店民宿大数据
     */
    private Integer rankTotal;

    /**
     * 查询酒店评价排行-酒店民宿大数据
     */
    private Map<Integer, List<BaseVO>> evaluateRankBigData;

    /**
     * 查询酒店满意度排行-酒店民宿大数据
     */
    private Map<Integer, List<EvaluateSatisfactionRankDTO>> satisfactionRankBigData;

    /**
     * 查询评价热词排行-酒店民宿大数据
     */
    private List<BaseVO> hotRankBigData;

    /**
     * 查询评价量趋势、同比、环比-酒店民宿大数据
     */
    private List<AnalysisBaseInfo> evaluateAnalysisBigData;

    /**
     * 查询评价满意度趋势、同比、环比-酒店民宿大数据
     */
    private List<AnalysisBaseInfo> satisfactionAnalysisBigData;

    /**
     * 查询房型价格趋势-酒店民宿大数据
     */
    private List<RoomPriceAnalysisDTO> roomPriceAnalysisBigData;

}
