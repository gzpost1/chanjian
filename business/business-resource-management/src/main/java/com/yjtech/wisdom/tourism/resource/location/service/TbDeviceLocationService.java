package com.yjtech.wisdom.tourism.resource.location.service;

import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisServiceImpl;
import com.yjtech.wisdom.tourism.resource.location.entity.TbDeviceLocationEntity;
import com.yjtech.wisdom.tourism.resource.location.mapper.TbDeviceLocationMapper;
import com.yjtech.wisdom.tourism.resource.location.vo.StaticsNumVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定位设备 服务实现类
 *
 * @author Mujun
 * @since 2021-07-07
 */
@Service
public class TbDeviceLocationService extends BaseMybatisServiceImpl<TbDeviceLocationMapper, TbDeviceLocationEntity> {
    public List<StaticsNumVo> staticsNum() {
        return baseMapper.staticsNum();
    }
}
