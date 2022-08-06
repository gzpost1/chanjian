package com.yjtech.wisdom.tourism.resource.industry.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.resource.industry.entity.IndustryFundsEntity;
import com.yjtech.wisdom.tourism.resource.industry.mapper.IndustryFundsMapper;
import com.yjtech.wisdom.tourism.resource.industry.vo.IndustryFundsCreateVO;
import com.yjtech.wisdom.tourism.resource.industry.vo.IndustryFundsQueryVO;
import com.yjtech.wisdom.tourism.resource.industry.vo.IndustryFundsUpdateVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 产业资金管理(TbIndustryFunds)表服务实现类
 *
 * @author horadirm
 * @since 2022-08-06 10:13:36
 */
@Service
public class IndustryFundsService extends ServiceImpl<IndustryFundsMapper, IndustryFundsEntity> {



    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void create(IndustryFundsCreateVO vo) {
        IndustryFundsEntity entity = new IndustryFundsEntity();
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
    public void update(IndustryFundsUpdateVO vo) {
        IndustryFundsEntity entity = baseMapper.selectById(vo.getId());
        if (null == entity) {
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
    public void updateStatus(UpdateStatusParam updateStatusParam) {
        IndustryFundsEntity entity = baseMapper.selectById(updateStatusParam.getId());
        if (null == entity) {
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
    public IndustryFundsEntity queryForId(Long id) {
        return baseMapper.selectById(id);
    }

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<IndustryFundsEntity> queryForList(IndustryFundsQueryVO vo) {
        return baseMapper.queryForList(null, vo);
    }

    /**
     * 查询分页
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public IPage<IndustryFundsEntity> queryForPage(IndustryFundsQueryVO vo) {
        Page<IndustryFundsEntity> page = new Page<>(vo.getPageNo(), vo.getPageSize());
        page.setRecords(baseMapper.queryForList(page, vo));

        return page;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        baseMapper.deleteById(id);
    }

}