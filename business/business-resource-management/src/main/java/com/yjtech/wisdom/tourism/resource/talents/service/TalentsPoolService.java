package com.yjtech.wisdom.tourism.resource.talents.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.bean.index.DataStatisticsDTO;
import com.yjtech.wisdom.tourism.common.bean.index.DataStatisticsQueryVO;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.resource.talents.entity.TalentsPoolEntity;
import com.yjtech.wisdom.tourism.resource.talents.mapper.TalentsPoolMapper;
import com.yjtech.wisdom.tourism.resource.talents.vo.TalentsPoolCreateVO;
import com.yjtech.wisdom.tourism.resource.talents.vo.TalentsPoolQueryVO;
import com.yjtech.wisdom.tourism.resource.talents.vo.TalentsPoolUpdateVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 人才库管理(TbTalentsPool)表服务实现类
 *
 * @author horadirm
 * @since 2022-08-06 09:37:16
 */
@Service
public class TalentsPoolService extends ServiceImpl<TalentsPoolMapper, TalentsPoolEntity> {



    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void create(TalentsPoolCreateVO vo){
        TalentsPoolEntity entity = new TalentsPoolEntity();
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
    public void update(TalentsPoolUpdateVO vo){
        TalentsPoolEntity entity = baseMapper.selectById(vo.getId());
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
        TalentsPoolEntity entity = baseMapper.selectById(updateStatusParam.getId());
        if(null == entity){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        entity.setStatus(updateStatusParam.getStatus());

        baseMapper.updateById(entity);
    }

    /**
     * 根据id查询信息
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public TalentsPoolEntity queryForId(Long id){
        return baseMapper.selectById(id);
    }

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<TalentsPoolEntity> queryForList(TalentsPoolQueryVO vo){
        return baseMapper.selectList(buildQueryWrapper(vo));
    }

    /**
     * 查询分页
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public IPage<TalentsPoolEntity> queryForPage(TalentsPoolQueryVO vo){
        Page<TalentsPoolEntity> page = new Page<>(vo.getPageNo(), vo.getPageSize());

        return baseMapper.selectPage(page, buildQueryWrapper(vo));
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id){
        baseMapper.deleteById(id);
    }

    /**
     * 查询数据统计
     *
     * @return
     */
    @Transactional(readOnly = true)
    public DataStatisticsDTO queryDataStatistics(){
        DataStatisticsDTO dto = baseMapper.queryDataStatisticsByDuration(new DataStatisticsQueryVO(null));
        dto.setTotalNum(baseMapper.selectCount(new LambdaQueryWrapper<>()));

        return dto;
    }

    /**
     * 构建查询参数
     *
     * @param vo
     * @return
     */
    private LambdaQueryWrapper<TalentsPoolEntity> buildQueryWrapper(TalentsPoolQueryVO vo){
        return new LambdaQueryWrapper<TalentsPoolEntity>()
                .like(StringUtils.isNotBlank(vo.getName()), TalentsPoolEntity::getName, vo.getName())
                .eq(null != vo.getStatus(), TalentsPoolEntity::getStatus, vo.getStatus())
                .orderByDesc(TalentsPoolEntity::getUpdateTime);
    }


}