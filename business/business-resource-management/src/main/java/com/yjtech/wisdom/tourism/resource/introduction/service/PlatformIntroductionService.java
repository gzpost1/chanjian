package com.yjtech.wisdom.tourism.resource.introduction.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.resource.introduction.entity.PlatformIntroductionEntity;
import com.yjtech.wisdom.tourism.resource.introduction.mapper.PlatformIntroductionMapper;
import com.yjtech.wisdom.tourism.resource.introduction.vo.PlatformIntroductionCreateVO;
import com.yjtech.wisdom.tourism.resource.introduction.vo.PlatformIntroductionQueryVO;
import com.yjtech.wisdom.tourism.resource.introduction.vo.PlatformIntroductionUpdateVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 平台介绍(TbPlatformIntroduction)表服务实现类
 *
 * @author horadirm
 * @since 2022-07-07 13:51:16
 */
@Service
public class PlatformIntroductionService extends ServiceImpl<PlatformIntroductionMapper, PlatformIntroductionEntity> {


    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void create(PlatformIntroductionCreateVO vo){
        //查询存在
        int exist = baseMapper.selectCount(new QueryWrapper<>());
        if(exist > 0){
            throw new CustomException(ErrorCode.BUSINESS_EXCEPTION, "新增失败：平台介绍已存在");
        }
        PlatformIntroductionEntity entity = new PlatformIntroductionEntity();
        entity.buildCreate(vo);

        baseMapper.insert(entity);
    }

    /**
     * 编辑
     *
     * @param vo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(PlatformIntroductionUpdateVO vo){
        PlatformIntroductionEntity entity = baseMapper.selectById(vo.getId());
        if(null == entity){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        entity.buildUpdate(vo);

        baseMapper.updateById(entity);
    }

    /**
     * 更新状态
     *
     * @param updateStatusParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(UpdateStatusParam updateStatusParam){
        PlatformIntroductionEntity entity = baseMapper.selectById(updateStatusParam.getId());
        if(null == entity){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        entity.setStatus(updateStatusParam.getStatus());

        baseMapper.updateById(entity);
    }

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<PlatformIntroductionEntity> queryForList(PlatformIntroductionQueryVO vo){
        return baseMapper.selectList(new LambdaQueryWrapper<PlatformIntroductionEntity>()
                .eq(null != vo.getStatus(), PlatformIntroductionEntity::getStatus, vo.getStatus())
                .like(StringUtils.isNotBlank(vo.getName()), PlatformIntroductionEntity::getName, vo.getName()));
    }

    /**
     * 查询分页
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public IPage<PlatformIntroductionEntity> queryForPage(PlatformIntroductionQueryVO vo){
        return baseMapper.selectPage(new Page<>(vo.getPageNo(), vo.getPageSize()), new LambdaQueryWrapper<PlatformIntroductionEntity>()
                .eq(null != vo.getStatus(), PlatformIntroductionEntity::getStatus, vo.getStatus())
                .like(StringUtils.isNotBlank(vo.getName()), PlatformIntroductionEntity::getName, vo.getName()));
    }

}