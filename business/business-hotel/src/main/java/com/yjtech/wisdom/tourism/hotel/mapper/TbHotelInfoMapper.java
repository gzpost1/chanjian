package com.yjtech.wisdom.tourism.hotel.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.hotel.dto.HotelScreenDetailDTO;
import com.yjtech.wisdom.tourism.hotel.entity.TbHotelInfoEntity;
import com.yjtech.wisdom.tourism.hotel.vo.HotelScreenQueryVO;
import com.yjtech.wisdom.tourism.hotel.vo.StaticNumVo;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author MJ~
 * @since 2020-08-05
 */
public interface TbHotelInfoMapper extends BaseMybatisMapper<TbHotelInfoEntity> {
    List<TbHotelInfoEntity> apiList(
            Page<TbHotelInfoEntity> page, @Param("params") TbHotelInfoEntity params);

    List<StaticNumVo> staticNum(@Param("areaCode") String areaCode);

    /**
     * 查询大屏分页
     * @param params
     * @return
     */
    List<HotelScreenDetailDTO> queryScreenPage(@Param("params") HotelScreenQueryVO params);

}
