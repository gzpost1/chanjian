package com.yjtech.wisdom.tourism.marketing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.marketing.entity.TbMarketingEvaluateEntity;
import com.yjtech.wisdom.tourism.marketing.entity.TbMarketingHotelRoomEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 营销推广 酒店房型
 *
 * @Author horadirm
 * @Date 2020/11/26 9:28
 */
public interface MarketingHotelRoomMapper extends BaseMapper<TbMarketingHotelRoomEntity> {

    /**
     * 批量新增
     * @param params
     */
    void insertBatch(@Param("params") List<TbMarketingHotelRoomEntity> params);

}
