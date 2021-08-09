package com.yjtech.wisdom.tourism.resource.wifi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import com.yjtech.wisdom.tourism.resource.wifi.dto.WifiPageQueryDto;
import com.yjtech.wisdom.tourism.resource.wifi.entity.WifiEntity;
import com.yjtech.wisdom.tourism.resource.wifi.vo.WifiHotVo;
import com.yjtech.wisdom.tourism.resource.wifi.vo.WifiVo;
import org.apache.ibatis.annotations.Param;

public interface WifiMapper extends BaseMapper<WifiEntity> {

    IPage<WifiVo> queryForPage(Page page, @Param("params") WifiPageQueryDto query);

    IPage<WifiHotVo> queryHotWifi(Page page, @Param("params") PageQuery query);
}
