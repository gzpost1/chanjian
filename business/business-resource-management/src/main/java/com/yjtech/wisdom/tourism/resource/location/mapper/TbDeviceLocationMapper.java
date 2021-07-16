package com.yjtech.wisdom.tourism.resource.location.mapper;

import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisMapper;
import com.yjtech.wisdom.tourism.resource.location.entity.TbDeviceLocationEntity;
import com.yjtech.wisdom.tourism.resource.location.vo.StaticsNumVo;

import java.util.List;

/**
 * <p>
 * 定位设备 Mapper 接口
 * </p>
 *
 * @author MJ~
 * @since 2021-07-07
 */
public interface TbDeviceLocationMapper extends BaseMybatisMapper<TbDeviceLocationEntity> {
    List<StaticsNumVo> staticsNum();
}
