package com.yjtech.wisdom.tourism.systemconfig.temp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.bean.SelectVo;
import com.yjtech.wisdom.tourism.systemconfig.temp.dto.SystemconfigTempPageQueryDto;
import com.yjtech.wisdom.tourism.systemconfig.temp.entity.SystemconfigTempEntity;
import com.yjtech.wisdom.tourism.systemconfig.temp.vo.SystemconfigTempVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemconfigTempMapper extends BaseMapper<SystemconfigTempEntity> {
    int batchInsert(@Param("list") List<SystemconfigTempEntity> list);

    IPage<SystemconfigTempVo> queryForPage(Page page, @Param("params") SystemconfigTempPageQueryDto query);

    List<SystemconfigTempVo> queryForTempList();

    SystemconfigTempEntity getById(Long id);

    Integer findTempMenusNum(Long id);

}