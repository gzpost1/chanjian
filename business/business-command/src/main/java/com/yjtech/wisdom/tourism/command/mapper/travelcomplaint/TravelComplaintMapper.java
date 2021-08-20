package com.yjtech.wisdom.tourism.command.mapper.travelcomplaint;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.command.dto.travelcomplaint.TravelComplaintListDTO;
import com.yjtech.wisdom.tourism.command.dto.travelcomplaint.TravelComplaintStatusStatisticsDTO;
import com.yjtech.wisdom.tourism.command.entity.travelcomplaint.TravelComplaintEntity;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintQueryVO;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintScreenQueryVO;
import com.yjtech.wisdom.tourism.common.bean.AnalysisMonthChartInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.sms.MessageCallDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 旅游投诉(TbTravelComplaint)表数据库访问层
 *
 * @author horadirm
 * @since 2021-07-21 09:03:47
 */
public interface TravelComplaintMapper extends BaseMapper<TravelComplaintEntity> {


    /**
     * 根据id查询实体信息
     * @param id
     * @return
     */
    TravelComplaintEntity queryEntityById(@Param("id") Long id);

    /**
     * 查询分页
     * @param page
     * @param params
     * @return
     */
    IPage<TravelComplaintListDTO> queryForPage(Page page, @Param("params") TravelComplaintQueryVO params);

    /**
     * 查询状态统计
     * @param params
     * @return
     */
    TravelComplaintStatusStatisticsDTO queryStatusStatistics(@Param("params") TravelComplaintQueryVO params);

    /**
     * 查询旅游投诉类型分布
     * @param params
     * @return
     */
    List<BasePercentVO> queryComplaintTypeDistribution(@Param("params") TravelComplaintScreenQueryVO params);

    /**
     * 查询旅游投诉状态分布
     * @param params
     * @return
     */
    List<BasePercentVO> queryComplaintStatusDistribution(@Param("params") TravelComplaintScreenQueryVO params);

    /**
     * 查询旅游投诉类型Top排行
     * @param params
     * @return
     */
    List<BaseVO> queryComplaintTopByType(@Param("params") TravelComplaintScreenQueryVO params);

    /**
     * 查询旅游投诉今年搜索月趋势
     * @param params
     * @return
     */
    List<AnalysisMonthChartInfo> queryComplaintCurrentAnalysisMonthInfo(@Param("params") TravelComplaintScreenQueryVO params);

    /**
     * 查询旅游投诉去年搜索月趋势
     * @param params
     * @return
     */
    List<AnalysisMonthChartInfo> queryComplaintLastAnalysisMonthInfo(@Param("params") TravelComplaintScreenQueryVO params);

    /**
     * 通过事件id，查询事件信息
     * @param ids
     * @return
     */
    List<MessageCallDto> queryEvent(@Param("ids") Long[] ids);



}
