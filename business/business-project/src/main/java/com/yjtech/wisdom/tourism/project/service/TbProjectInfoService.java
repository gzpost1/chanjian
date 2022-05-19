package com.yjtech.wisdom.tourism.project.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.project.dto.ProjectQuery;
import com.yjtech.wisdom.tourism.project.entity.TbProjectInfoEntity;
import com.yjtech.wisdom.tourism.project.entity.TbProjectLabelRelationEntity;
import com.yjtech.wisdom.tourism.project.entity.TbProjectResourceEntity;
import com.yjtech.wisdom.tourism.project.mapper.TbProjectInfoMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    /**
     * 查询列表
     *
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public List<TbProjectInfoEntity> queryForList(ProjectQuery params){
        List<TbProjectInfoEntity> list = baseMapper.queryForList(null, params);
        //构建已选中项目标签id列表
        if(CollectionUtils.isNotEmpty(list)){
            for(TbProjectInfoEntity entity : list){
                entity.setPitchOnLabelIdList(tbProjectLabelRelationService.queryForLabelIdListByProjectId(entity.getId()));
            }
        }
        return list;
    }

    /**
     * 查询分页
     *
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public IPage<TbProjectInfoEntity> queryForPage(ProjectQuery params){
        Page<TbProjectInfoEntity> page = new Page<>(params.getPageNo(), params.getPageSize());
        List<TbProjectInfoEntity> records = baseMapper.queryForList(page, params);
        //构建已选中项目标签id列表
        if(CollectionUtils.isNotEmpty(records)){
            for(TbProjectInfoEntity entity : records){
                entity.setPitchOnLabelIdList(tbProjectLabelRelationService.queryForLabelIdListByProjectId(entity.getId()));
            }
            page.setRecords(records);
        }
        page.setRecords(records);
        return page;
    }

}
