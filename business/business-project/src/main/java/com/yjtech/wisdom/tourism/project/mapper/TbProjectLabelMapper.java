package com.yjtech.wisdom.tourism.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.project.entity.TbProjectLabelEntity;

import java.util.List;

/**
 * 项目标签(TbProjectLabel)表数据库访问层
 *
 * @author horadirm
 * @since 2022-05-18 17:16:29
 */
public interface TbProjectLabelMapper extends BaseMapper<TbProjectLabelEntity> {

    /**
     * 大屏-数据统计-项目特色标签项目数占比
     */
    List<BaseVO> queryProjectLabelTrend();
}