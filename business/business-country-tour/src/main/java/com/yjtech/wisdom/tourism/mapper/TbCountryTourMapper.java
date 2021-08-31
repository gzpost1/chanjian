package com.yjtech.wisdom.tourism.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.bean.SelectVo;
import com.yjtech.wisdom.tourism.entity.TbCountryTour;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.yjtech.wisdom.tourism.entity.TbCountryTour
 */
public interface TbCountryTourMapper extends BaseMybatisMapper<TbCountryTour> {
    List<SelectVo> statistic();

    List<TbCountryTour> listExtra(Page<TbCountryTour> page, @Param("params") TbCountryTour params);
}




