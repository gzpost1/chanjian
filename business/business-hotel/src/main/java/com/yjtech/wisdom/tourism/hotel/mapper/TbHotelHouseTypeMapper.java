package com.yjtech.wisdom.tourism.hotel.mapper;

import com.yjtech.wisdom.tourism.hotel.entity.TbHotelHouseTypeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author MJ~
 * @since 2020-08-13
 */
public interface TbHotelHouseTypeMapper extends BaseMybatisMapper<TbHotelHouseTypeEntity> {

    List<TbHotelHouseTypeEntity> selectByHotelId (@Param("id") long id);
}
