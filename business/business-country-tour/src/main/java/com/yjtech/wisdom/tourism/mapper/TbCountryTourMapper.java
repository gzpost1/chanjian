package com.yjtech.wisdom.tourism.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.bean.SelectVo;
import com.yjtech.wisdom.tourism.entity.TbCountryTour;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisMapper;

import java.util.List;

/**
 * @Entity com.yjtech.wisdom.tourism.entity.TbCountryTour
 */
public interface TbCountryTourMapper extends BaseMybatisMapper<TbCountryTour> {
    List<SelectVo> statistic();
}




