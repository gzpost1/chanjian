package com.yjtech.wisdom.tourism.project.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.constant.CacheKeyContants;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.project.entity.TbProjectLabelEntity;
import com.yjtech.wisdom.tourism.project.entity.TbProjectLabelRelationEntity;
import com.yjtech.wisdom.tourism.project.mapper.TbProjectLabelMapper;
import com.yjtech.wisdom.tourism.project.vo.ProjectLabelCreateVO;
import com.yjtech.wisdom.tourism.project.vo.ProjectLabelQueryVO;
import com.yjtech.wisdom.tourism.project.vo.ProjectLabelUpdateVO;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 项目标签(TbProjectLabel)表服务实现类
 *
 * @author horadirm
 * @since 2022-05-18 17:16:30
 */
@Service
public class TbProjectLabelService extends ServiceImpl<TbProjectLabelMapper, TbProjectLabelEntity> {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private TbProjectLabelRelationService tbProjectLabelRelationService;

    /**
     * 新增
     *
     * @param vo
     */
    @Transactional(rollbackFor = Exception.class)
    public void create(ProjectLabelCreateVO vo){
        TbProjectLabelEntity entity = new TbProjectLabelEntity();
        entity.buildCreate(vo);

        //操作成功，清除缓存
        clearCache(baseMapper.insert(entity));
    }

    /**
     * 编辑
     *
     * @param vo
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(ProjectLabelUpdateVO vo){
        TbProjectLabelEntity entity = baseMapper.selectById(vo.getId());
        if(null == entity){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        entity.buildUpdate(vo);

        //操作成功，清除缓存
        clearCache(baseMapper.updateById(entity));
    }

    /**
     * 查询列表
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<TbProjectLabelEntity> queryForList(ProjectLabelQueryVO vo){
        return baseMapper.selectList(new LambdaQueryWrapper<TbProjectLabelEntity>()
                .like(StringUtils.isNotBlank(vo.getName()),TbProjectLabelEntity::getName, vo.getName())
                .eq(null != vo.getStatus(), TbProjectLabelEntity::getStatus, vo.getStatus()));
    }

    /**
     * 查询分页
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public IPage<TbProjectLabelEntity> queryForPage(ProjectLabelQueryVO vo){
        Page<TbProjectLabelEntity> page = new Page<>(vo.getPageNo(), vo.getPageSize());
        return baseMapper.selectPage(page, new LambdaQueryWrapper<TbProjectLabelEntity>()
                .like(StringUtils.isNotBlank(vo.getName()),TbProjectLabelEntity::getName, vo.getName())
                .eq(null != vo.getStatus(), TbProjectLabelEntity::getStatus, vo.getStatus()));
    }

    /**
     * 更新状态
     *
     * @param updateStatusParam
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(UpdateStatusParam updateStatusParam){
        //获取信息
        TbProjectLabelEntity entity = baseMapper.selectById(updateStatusParam.getId());
        if(null == entity){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        entity.setStatus(updateStatusParam.getStatus());

        //操作成功，清除缓存
        clearCache(baseMapper.updateById(entity));
    }

    /**
     * 删除
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id){
        int result = baseMapper.deleteById(id);
        if(result > 0){
            //同步删除该项目-标签的关联
            tbProjectLabelRelationService.remove(new LambdaQueryWrapper<TbProjectLabelRelationEntity>()
                            .eq(TbProjectLabelRelationEntity::getLabelId, id));
            //同步删除标签缓存
            redisCache.deleteObject(CacheKeyContants.PROJECT_LABEL_INFO_PREFIX);
        }
    }

    /**
     * 查询可用标签列表
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<TbProjectLabelEntity> queryEnableForList(){
        List<TbProjectLabelEntity> list = redisCache.getCacheList(CacheKeyContants.PROJECT_LABEL_INFO_PREFIX);

        if(CollectionUtils.isEmpty(list)){
            list = baseMapper.selectList(new LambdaQueryWrapper<TbProjectLabelEntity>()
                    .eq(TbProjectLabelEntity::getStatus, EntityConstants.ENABLED));
            //更新缓存
            redisCache.setCacheList(CacheKeyContants.PROJECT_LABEL_INFO_PREFIX, list);
        }

        return list;
    }

    /**
     * 清除缓存
     * @param result
     */
    private void clearCache(Integer result){
        if(result > 0){
            redisCache.deleteObject(CacheKeyContants.PROJECT_LABEL_INFO_PREFIX);
        }
    }

}