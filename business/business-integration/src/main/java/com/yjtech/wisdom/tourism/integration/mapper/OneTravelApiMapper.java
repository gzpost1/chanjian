package com.yjtech.wisdom.tourism.integration.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.AnalysisMonthChartInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelAreaVisitStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelComplaintListBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelMagicVisitPvBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelVisitStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.OneTravelQueryVO;
import com.yjtech.wisdom.tourism.mybatis.entity.AreaBaseVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 一码游Api服务
 *
 * @Author horadirm
 * @Date 2021/5/24 19:02
 */
@DS("oneTravel")
public interface OneTravelApiMapper {

    /**
     * 查询访问统计
     * @return
     */
    OneTravelVisitStatisticsBO queryVisitStatistics();

    /**
     * 查询市级访问统计
     * @param params
     * @return
     */
    List<OneTravelAreaVisitStatisticsBO> queryCityVisitStatistics(@Param("params") AreaBaseVO params);

    /**
     * 查询所有省级、直辖市、自治区、特别行政区访问统计
     * @return
     */
    List<OneTravelAreaVisitStatisticsBO> queryProvinceVisitStatistics();

    /**
     * 查询一码游补充数据
     * @return
     */
    OneTravelMagicVisitPvBO queryDailySum();

    /**
     * 查询微信城市分布
     * @return
     */
    Long queryUserCityTotalSum();

    /**
     * 查询微信省级、直辖市、自治区、特别行政区分布
     * @return
     */
    Long queryUserProvinceTotalSum();

    /**
     * 查询投诉统计
     * @param params
     * @return
     */
    Integer queryComplaintStatistics(@Param("params") OneTravelQueryVO params);

    /**
     * 查询投诉列表
     * @param page
     * @param params
     * @return
     */
    IPage<OneTravelComplaintListBO> queryComplaintForPage(IPage<OneTravelComplaintListBO> page, @Param("params") OneTravelQueryVO params);

    /**
     * 查询投诉状态分布
     * @param params
     * @return
     */
    List<BasePercentVO> queryComplaintDistribution(@Param("params") OneTravelQueryVO params);

    /**
     * 查询用户年龄分布
     * @return
     */
    List<BasePercentVO> queryUserAgeDistribution();

    /**
     * 查询一码游投诉今年搜索月趋势
     * @param params
     * @return
     */
    List<AnalysisMonthChartInfo> queryComplaintCurrentAnalysisMonthInfo(@Param("params") OneTravelQueryVO params);

    /**
     * 查询一码游投诉去年搜索月趋势
     * @param params
     * @return
     */
    List<AnalysisMonthChartInfo> queryComplaintLastAnalysisMonthInfo(@Param("params") OneTravelQueryVO params);




}
