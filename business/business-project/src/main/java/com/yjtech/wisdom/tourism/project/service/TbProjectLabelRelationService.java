package com.yjtech.wisdom.tourism.project.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.utils.IdWorker;
import com.yjtech.wisdom.tourism.project.entity.TbProjectLabelRelationEntity;
import com.yjtech.wisdom.tourism.project.mapper.TbProjectLabelRelationMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 项目-标签关系(TbProjectLabelRelation)表服务实现类
 *
 * @author horadirm
 * @since 2022-05-18 18:43:51
 */
@Service
public class TbProjectLabelRelationService extends ServiceImpl<TbProjectLabelRelationMapper, TbProjectLabelRelationEntity> {


    /**
     * 构建
     *
     * @param projectId
     * @param labelIdList
     */
    @Transactional(rollbackFor = Exception.class)
    public void build(Long projectId, List<Long> labelIdList){
        if(CollectionUtils.isNotEmpty(labelIdList)){
            //物理删除该项目历史标签数据
            remove(new LambdaQueryWrapper<TbProjectLabelRelationEntity>()
                    .eq(TbProjectLabelRelationEntity::getProjectId, projectId));
            //构建列表
            List<TbProjectLabelRelationEntity> buildList = Lists.newArrayList();
            for(Long labelId : labelIdList){
                buildList.add(new TbProjectLabelRelationEntity(IdWorker.getInstance().nextId(), new Date(), projectId, labelId));
            }

            saveBatch(buildList, buildList.size());
        }
    }

    /**
     * 根据项目id获取标签id列表
     *
     * @param projectId
     * @return
     */
    @Transactional(readOnly = true)
    public List<Long> queryForLabelIdListByProjectId(Long projectId){
        return baseMapper.queryForLabelIdListByProjectId(projectId);
    }


}