package com.yjtech.wisdom.tourism.marketing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.marketing.entity.MarketingHotelRoomEntity;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.NewestRoomScreenDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.RoomPriceAnalysisDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.RoomTypePriceScreenDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.RoomScreenQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 营销推广 酒店房型
 *
 * @Author horadirm
 * @Date 2020/11/26 9:28
 */
public interface MarketingHotelRoomMapper extends BaseMapper<MarketingHotelRoomEntity> {

    /**
     * 批量新增
     * @param params
     */
    void insertBatch(@Param("params") List<MarketingHotelRoomEntity> params);

    /**
     * 查询房型价格统计
     * @param params
     * @return
     */
    RoomTypePriceScreenDTO queryRoomPriceStatistics(@Param("params") RoomScreenQueryVO params);

    /**
     * 查询最新房型列表
     * @param params
     * @return
     */
    List<NewestRoomScreenDTO> queryNewestRoomInfo(@Param("params") RoomScreenQueryVO params);

    /**
     * 查询房型价格趋势
     * @param params
     * @return
     */
    List<RoomPriceAnalysisDTO> queryRoomPriceAnalysis(@Param("params") RoomScreenQueryVO params);

    /**
     * 查询房型价格分布
     * @param params
     * @return
     */
    List<BaseVO> queryRoomTypePriceDistribution(@Param("params") RoomScreenQueryVO params);

}
