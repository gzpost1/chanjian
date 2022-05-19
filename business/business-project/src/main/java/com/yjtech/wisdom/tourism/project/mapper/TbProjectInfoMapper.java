package com.yjtech.wisdom.tourism.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.project.dto.ProjectQuery;
import com.yjtech.wisdom.tourism.project.entity.TbProjectInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbProjectInfoMapper extends BaseMapper<TbProjectInfoEntity> {


    /**
     * 查询列表
     *
     * @param page
     * @param params
     * @return
     */
    List<TbProjectInfoEntity> queryForList(Page page, @Param("params") ProjectQuery params);

}