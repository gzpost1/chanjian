package com.yjtech.wisdom.tourism.integration.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.AreaBaseVO;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelComplaintListBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelMagicVisitPvBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelVisitStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.OneTravelQueryVO;
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
     * 查询市级访问统计
     * @param params
     * @return
     */
    List<OneTravelVisitStatisticsBO> queryVisitStatistics(@Param("params") AreaBaseVO params);

    /**
     * 查询所有省级、直辖市、自治区、特别行政区访问统计
     * @return
     */
    List<OneTravelVisitStatisticsBO> queryProvinceVisitStatistics();

    /**
     * 查询一码游补充数据
     * @return
     */
    OneTravelMagicVisitPvBO queryDailySum();

    /**
     * 查询微信城市分布
     * @return
     */
    Long userCityTotalSum();

    /**
     * 查询微信省级、直辖市、自治区、特别行政区分布
     * @return
     */
    Long userProvinceTotalSum();

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




}
