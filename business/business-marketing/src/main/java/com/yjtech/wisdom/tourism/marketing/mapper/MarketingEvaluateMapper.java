package com.yjtech.wisdom.tourism.marketing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.marketing.entity.TbMarketingEvaluateEntity;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.AnalysisMonthChartInfo;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.evaluate.*;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateRankingVO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateScreenQueryVO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.ScreenAnalysisQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 营销推广 评价信息
 *
 * @Author horadirm
 * @Date 2020/11/20 16:10
 */
public interface MarketingEvaluateMapper extends BaseMapper<TbMarketingEvaluateEntity> {

    /**
     * 批量新增
     * @param params
     */
    void insertBatch(@Param("params") List<TbMarketingEvaluateEntity> params);

    /**
     * 获取评论排名信息
     * @param params
     * @return
     */
    List<EvaluateRankingInfo> getRankingInfo(@Param("params") EvaluateRankingVO params);

    /**
     * 获取市/区县级图表
     * @param params
     * @return
     */
    List<EvaluateChartInfo> getChartByCity(@Param("params") ScreenAnalysisQueryVO params);

    /**
     * 获取省级图表
     * @param params
     * @return
     */
    List<EvaluateChartInfo> getChartByProvince(@Param("params") ScreenAnalysisQueryVO params);

    /**
     * 获取行业分布
     * @param params
     * @return
     */
    List<EvaluateDistributionInfo> getBusinessInfo(@Param("params") ScreenAnalysisQueryVO params);

    /**
     * 获取来源分布
     * @param params
     * @return
     */
    List<EvaluateDistributionInfo> getSourcesInfo(@Param("params") ScreenAnalysisQueryVO params);

    /**
     * 获取评论日趋势统计
     * @param areaCode
     * @return
     */
    EvaluateAnalysisDayDTO getAnalysisDayStatistics(@Param("areaCode") String areaCode);

    /**
     * 获取评论日趋势信息
     * @param areaCode
     * @return
     */
    List<EvaluateAnalysisDayChartInfo> getAnalysisDayInfo(@Param("areaCode") String areaCode);

    /**
     * 获取评论月趋势统计
     * @param areaCode
     * @return
     */
    EvaluateAnalysisMonthDTO getAnalysisMonthStatistics(@Param("areaCode") String areaCode);

    /**
     * 获取今年评论月趋势信息
     * @param areaCode
     * @return
     */
    List<AnalysisMonthChartInfo> getCurrentAnalysisMonthInfo(@Param("areaCode") String areaCode);

    /**
     * 获取去年评论月趋势信息
     * @param areaCode
     * @return
     */
    List<AnalysisMonthChartInfo> getLastAnalysisMonthInfo(@Param("areaCode") String areaCode);

    /**
     * 查询评价类型分布
     * @param params
     * @return
     */
    List<BasePercentVO> queryEvaluateTypeDistribution(@Param("params") EvaluateScreenQueryVO params);

}
