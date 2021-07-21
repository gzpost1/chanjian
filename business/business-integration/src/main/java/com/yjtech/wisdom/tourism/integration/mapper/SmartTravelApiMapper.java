package com.yjtech.wisdom.tourism.integration.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.yjtech.wisdom.tourism.integration.pojo.bo.smarttravel.*;
import com.yjtech.wisdom.tourism.integration.pojo.vo.SmartTravelQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 景区中心Api服务
 *
 * @Author horadirm
 * @Date 2021/5/24 19:05
 */
@DS("smartTravel")
public interface SmartTravelApiMapper {

    /**
     * 查询预约统计
     *
     * @param params
     * @return
     */
    SmartTravelReservationStatisticsBO queryReservationStatistics(@Param("params") SmartTravelQueryVO params);

    /**
     * 查询景区列表
     *
     * @param params
     * @return
     */
    List<SmartTravelScenicInfoBO> queryScenicList(@Param("params") SmartTravelQueryVO params);

    /**
     * 查询景区预约列表
     * @param params
     * @return
     */
    List<SmartTravelReservationRankListBO> queryScenicReservationList(@Param("params") SmartTravelQueryVO params);

    /**
     * 查询区域预约列表
     * @param params
     * @return
     */
    List<SmartTravelAreaReservationListBO> queryAreaReservationList(@Param("params") SmartTravelQueryVO params);

    /**
     * 查询景区预约趋势列表
     * @param params
     * @return
     */
    List<SmartTravelScenicReservationAnalysisBO> queryScenicReservationAnalysisList(@Param("params") SmartTravelQueryVO params);

}
