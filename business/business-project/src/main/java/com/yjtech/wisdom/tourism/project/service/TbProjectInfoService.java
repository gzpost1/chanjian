package com.yjtech.wisdom.tourism.project.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.project.entity.TbProjectInfoEntity;
import com.yjtech.wisdom.tourism.project.entity.TbProjectLabelRelationEntity;
import com.yjtech.wisdom.tourism.project.entity.TbProjectResourceEntity;
import com.yjtech.wisdom.tourism.project.mapper.TbProjectInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TbProjectInfoService extends ServiceImpl<TbProjectInfoMapper, TbProjectInfoEntity> {

    @Autowired
    private TbProjectResourceService projectResourceService;
    @Autowired
    private TbProjectLabelRelationService tbProjectLabelRelationService;

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        this.removeById(id);

        LambdaUpdateWrapper<TbProjectResourceEntity> wrapper = new LambdaUpdateWrapper<TbProjectResourceEntity>();
        wrapper.eq(TbProjectResourceEntity::getProjectId,id);
        boolean result = projectResourceService.remove(wrapper);

        //同步删除该项目的标签关联
        if(result){
            tbProjectLabelRelationService.remove(new LambdaQueryWrapper<TbProjectLabelRelationEntity>()
                    .eq(TbProjectLabelRelationEntity::getProjectId, id));
        }
    }
}
