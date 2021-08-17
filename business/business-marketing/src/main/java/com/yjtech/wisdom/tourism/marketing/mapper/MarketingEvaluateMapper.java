package com.yjtech.wisdom.tourism.marketing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.bean.AnalysisMonthChartInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.marketing.entity.MarketingEvaluateEntity;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.HotelEvaluateSatisfactionRankDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateListDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 营销推广 评价信息
 *
 * @Author horadirm
 * @Date 2020/11/20 16:10
 */
public interface MarketingEvaluateMapper extends BaseMapper<MarketingEvaluateEntity> {

    /**
     * 批量新增
     *
     * @param params
     */
    void insertBatch(@Param("params") List<MarketingEvaluateEntity> params);

    /**
     * 查询评价统计
     *
     * @param params
     * @return
     */
    MarketingEvaluateStatisticsDTO queryEvaluateStatistics(@Param("params") EvaluateQueryVO params);

    /**
     * 查询评价类型分布
     *
     * @param params
     * @return
     */
    List<BasePercentVO> queryEvaluateTypeDistribution(@Param("params") EvaluateQueryVO params);

    /**
     * 查询评价热词排行
     *
     * @param params
     * @return
     */
    List<BaseVO> queryEvaluateHotRank(@Param("params") EvaluateQueryVO params);

    /**
     * 查询评价排行
     *
     * @param params
     * @return
     */
    List<BaseVO> queryEvaluateRank(@Param("params") EvaluateQueryVO params);

    /**
     * 查询满意度排行
     *
     * @param params
     * @return
     */
    List<HotelEvaluateSatisfactionRankDTO> queryEvaluateSatisfactionRank(@Param("params") EvaluateQueryVO params);

    /**
     * 查询评价分页列表
     *
     * @param params
     * @return
     */
    IPage<MarketingEvaluateListDTO> queryForPage(Page page, @Param("params") EvaluateQueryVO params);

    /**
     * 查询评价量今年搜索月趋势
     *
     * @param params
     * @return
     */
    List<AnalysisMonthChartInfo> queryEvaluateCurrentAnalysis(@Param("params") EvaluateQueryVO params);

    /**
     * 查询评价量去年搜索月趋势
     *
     * @param params
     * @return
     */
    List<AnalysisMonthChartInfo> queryEvaluateLastAnalysis(@Param("params") EvaluateQueryVO params);

    /**
     * 查询评价满意度今年搜索月趋势
     *
     * @param params
     * @return
     */
    List<AnalysisMonthChartInfo> queryEvaluateSatisfactionCurrentAnalysis(@Param("params") EvaluateQueryVO params);

    /**
     * 查询评价满意度去年搜索月趋势
     *
     * @param params
     * @return
     */
    List<AnalysisMonthChartInfo> queryEvaluateSatisfactionLastAnalysis(@Param("params") EvaluateQueryVO params);

    /**
     * 景区大数据-评价排行top5
     *
     * @param params
     * @return
     */
    IPage<BaseVO> queryEvaluateTop5(Page page, @Param("params") EvaluateQueryVO params);

    /**
     * 景区大数据-满意度排行TOP5
     *
     * @param params
     * @return
     */
    IPage<BaseVO> querySatisfactionTop5(Page page, @Param("params") EvaluateQueryVO params);

    /**
     * 景区分布—查询评价统计
     *
     * @param params
     * @return
     */
    List<BasePercentVO> queryScenicEvaluateTypeDistribution(@Param("params") EvaluateQueryVO params);

    /**
     * 景区分布—查询评价统计
     *
     * @param params
     * @return
     */
    MarketingEvaluateStatisticsDTO queryScenicEvaluateStatistics(@Param("params") EvaluateQueryVO params);

    /**
     * 景区分布——热度趋势
     *
     * @param query
     * @return
     */
    List<BaseVO> queryHeatTrend(@Param("params") EvaluateQueryVO query);

    /**
     * 景区分布——满意度趋势
     *
     * @param query
     * @return
     */
    List<BaseVO> querySatisfactionTrend(@Param("params") EvaluateQueryVO query);

    /**
     * 景区分布——热词
     *
     * @param params
     * @return
     */
    List<BaseVO> queryScenicHotRank(@Param("params") EvaluateQueryVO params);
}
