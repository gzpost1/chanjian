package com.yjtech.wisdom.tourism.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.project.entity.TbProjectLabelRelationEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目-标签关系(TbProjectLabelRelation)表数据库访问层
 *
 * @author horadirm
 * @since 2022-05-18 18:43:51
 */
public interface TbProjectLabelRelationMapper extends BaseMapper<TbProjectLabelRelationEntity> {


    /**
     * 根据项目id获取标签id列表
     *
     * @param projectId
     * @return
     */
    List<Long> queryForLabelIdListByProjectId(@Param("projectId") Long projectId);

}