package com.yjtech.wisdom.tourism.service;

import com.yjtech.wisdom.tourism.common.bean.SelectVo;
import com.yjtech.wisdom.tourism.entity.TbCountryTour;
import com.yjtech.wisdom.tourism.mapper.TbCountryTourMapper;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class TbCountryTourService extends BaseMybatisServiceImpl<TbCountryTourMapper, TbCountryTour> {
    public List<SelectVo> statistic() {
        return baseMapper.statistic();
    }
}




