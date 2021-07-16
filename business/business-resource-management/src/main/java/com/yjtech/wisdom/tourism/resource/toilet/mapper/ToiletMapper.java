package com.yjtech.wisdom.tourism.resource.toilet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.resource.toilet.dto.ToiletPageQueryDto;
import com.yjtech.wisdom.tourism.resource.toilet.entity.ToiletEntity;
import com.yjtech.wisdom.tourism.resource.toilet.vo.ToiletVo;
import org.apache.ibatis.annotations.Param;

public interface ToiletMapper extends BaseMapper<ToiletEntity> {

    IPage<ToiletVo> queryForPage(Page page, @Param("params") ToiletPageQueryDto query);
}