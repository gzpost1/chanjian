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
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.DictArea;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import com.yjtech.wisdom.tourism.position.entity.TbDictAreaEntity;
import com.yjtech.wisdom.tourism.position.service.TbDictAreaService;
import com.yjtech.wisdom.tourism.resource.talents.entity.TalentsPoolEntity;
import com.yjtech.wisdom.tourism.resource.talents.mapper.TalentsPoolMapper;
import com.yjtech.wisdom.tourism.resource.talents.vo.TalentsPoolCreateVO;
import com.yjtech.wisdom.tourism.resource.talents.vo.TalentsPoolQueryVO;
import com.yjtech.wisdom.tourism.resource.talents.vo.TalentsPoolUpdateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 人才库管理(TbTalentsPool)表服务实现类
 *
 * @author horadirm
 * @since 2022-08-06 09:37:16
 */
@Service
public class TalentsPoolService extends ServiceImpl<TalentsPoolMapper, TalentsPoolEntity> {
    @Autowired
    private TbDictAreaService dictAreaService;


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
        SysUser user = SecurityUtils.getLoginUser().getUser();
        List<DictArea> areas = user.getAreas();
        boolean hasCityRights = new HashSet<>(areas.stream()
                .map(DictArea::getCode)
                .collect(Collectors.toList())).containsAll(dictAreaService.subArea("5201")
                .stream()
                .map(TbDictAreaEntity::getCode)
                .collect(Collectors.toList()));
        boolean hasAllRights = hasCityRights || user.isAdmin();
        LambdaQueryWrapper<TalentsPoolEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!hasAllRights, BaseEntity::getCreateUser, user.getUserId());
        return wrapper
                .like(StringUtils.isNotBlank(vo.getName()), TalentsPoolEntity::getName, vo.getName())
                .eq(null != vo.getStatus(), TalentsPoolEntity::getStatus, vo.getStatus())
                .orderByDesc(TalentsPoolEntity::getUpdateTime);
    }


}