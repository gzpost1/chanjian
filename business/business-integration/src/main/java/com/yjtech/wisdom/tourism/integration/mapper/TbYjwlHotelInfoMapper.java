package com.yjtech.wisdom.tourism.integration.mapper;

import com.yjtech.wisdom.tourism.integration.entity.TbYjwlHotelInfoEntity;
import com.yjtech.wisdom.tourism.integration.pojo.bo.hotel.HotelSaleRankListBO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.HotelQueryVO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.HotelStaticsVo;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 酒店信息 Mapper 接口
 * </p>
 *
 * @author MJ~
 * @since 2021-05-24
 */
public interface TbYjwlHotelInfoMapper extends BaseMybatisMapper<TbYjwlHotelInfoEntity> {


    /**
     * 查询自营酒店销售列表
     * @param params
     * @return
     */
    List<HotelSaleRankListBO> querySelfHotelSaleList(@Param("params") HotelQueryVO params);

    /**
     * 查询美团、携程酒店销售列表
     * @param params
     * @return
     */
    List<HotelSaleRankListBO> queryYjwlHotelSaleList(@Param("params") HotelQueryVO params);

    /**
     * 查询酒店销售列表
     * @param params
     * @return
     */
    List<HotelSaleRankListBO> queryHotelSaleList(@Param("params") HotelQueryVO params);
    /**
     * 酒店类型数量统计
     * @param params
     * @return
     */
    List<HotelStaticsVo> hotelStatiscs(@Param("params") HotelQueryVO params);



}
