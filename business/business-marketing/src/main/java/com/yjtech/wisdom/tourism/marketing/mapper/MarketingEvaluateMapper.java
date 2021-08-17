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
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateScreenQueryVO;
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
     * @param params
     */
    void insertBatch(@Param("params") List<MarketingEvaluateEntity> params);

    /**
     * 查询评价统计
     * @param params
     * @return
     */
    MarketingEvaluateStatisticsDTO queryEvaluateStatistics(@Param("params") EvaluateScreenQueryVO params);

    /**
     * 查询评价类型分布
     * @param params
     * @return
     */
    List<BasePercentVO> queryEvaluateTypeDistribution(@Param("params") EvaluateScreenQueryVO params);

    /**
     * 查询评价热词排行
     * @param params
     * @return
     */
    List<BaseVO> queryEvaluateHotRank(@Param("params") EvaluateScreenQueryVO params);

    /**
     * 查询评价排行
     * @param params
     * @return
     */
    List<BaseVO> queryEvaluateRank(@Param("params") EvaluateScreenQueryVO params);

    /**
     * 查询满意度排行
     * @param params
     * @return
     */
    List<HotelEvaluateSatisfactionRankDTO> queryEvaluateSatisfactionRank(@Param("params") EvaluateScreenQueryVO params);

    /**
     * 查询评价分页列表
     * @param params
     * @return
     */
    IPage<MarketingEvaluateListDTO> queryForPage(Page page, @Param("params") EvaluateScreenQueryVO params);

    /**
     * 查询评价量今年搜索月趋势
     * @param params
     * @return
     */
    List<AnalysisMonthChartInfo> queryEvaluateCurrentAnalysis(@Param("params") EvaluateScreenQueryVO params);

    /**
     * 查询评价量去年搜索月趋势
     * @param params
     * @return
     */
    List<AnalysisMonthChartInfo> queryEvaluateLastAnalysis(@Param("params") EvaluateScreenQueryVO params);

    /**
     * 查询评价满意度今年搜索月趋势
     * @param params
     * @return
     */
    List<AnalysisMonthChartInfo> queryEvaluateSatisfactionCurrentAnalysis(@Param("params") EvaluateScreenQueryVO params);

    /**
     * 查询评价满意度去年搜索月趋势
     * @param params
     * @return
     */
    List<AnalysisMonthChartInfo> queryEvaluateSatisfactionLastAnalysis(@Param("params") EvaluateScreenQueryVO params);

}
