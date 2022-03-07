package com.yjtech.wisdom.tourism.project.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yjtech.wisdom.tourism.project.entity.TbProjectResourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.project.entity.TbProjectInfoEntity;
import com.yjtech.wisdom.tourism.project.mapper.TbProjectInfoMapper;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TbProjectInfoService extends ServiceImpl<TbProjectInfoMapper, TbProjectInfoEntity> {

    @Autowired
    private TbProjectResourceService projectResourceService;

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        this.removeById(id);

        LambdaUpdateWrapper<TbProjectResourceEntity> wrapper = new LambdaUpdateWrapper<TbProjectResourceEntity>();
        wrapper.eq(TbProjectResourceEntity::getProjectId,id);
        projectResourceService.remove(wrapper);
    }
}
