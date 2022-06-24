package com.yjtech.wisdom.tourism.bigscreen.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.bigscreen.dto.TbRegisterInfoParam;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbRegisterInfoEntity;
import com.yjtech.wisdom.tourism.bigscreen.mapper.TbRegisterInfoMapper;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 注册信息 服务实现类
 *
 * @author Mujun
 * @since 2022-03-02
 */
@Service
public class TbRegisterInfoService extends BaseMybatisServiceImpl<TbRegisterInfoMapper, TbRegisterInfoEntity> {

    public TbRegisterInfoEntity queryByPhone(String phone) {
        QueryWrapper<TbRegisterInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TbRegisterInfoEntity.PHONE, phone);
        TbRegisterInfoEntity tbRegisterInfoEntity = baseMapper.selectOne(queryWrapper);
        return tbRegisterInfoEntity;
    }

    public boolean checkPhone(String phone) {
        QueryWrapper<TbRegisterInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TbRegisterInfoEntity.PHONE, phone);
        List<TbRegisterInfoEntity> list = baseMapper.selectList(queryWrapper);
        if (list.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 大屏 根据类型找 企业 1.投资方 2.业态方 3.运营方
     *
     * @return
     */
    public IPage<TbRegisterInfoEntity> queryForPageByType(TbRegisterInfoParam param) {
        LambdaQueryWrapper<TbRegisterInfoEntity> queryWrapper = new LambdaQueryWrapper<>();

        if (1 == param.getType()) {
            queryWrapper.isNotNull(TbRegisterInfoEntity::getInvestmentLabel);
            queryWrapper.like(!StringUtils.isEmpty(param.getLabel()), TbRegisterInfoEntity::getInvestmentLabel, param.getLabel());
        }
        else if (2 == param.getType()) {
            queryWrapper.isNotNull(TbRegisterInfoEntity::getCommercialLabel);
            queryWrapper.like(!StringUtils.isEmpty(param.getLabel()), TbRegisterInfoEntity::getCommercialLabel, param.getLabel());
        }
        else if (3 == param.getType()) {
            queryWrapper.isNotNull(TbRegisterInfoEntity::getOperationLabel);
            queryWrapper.like(!StringUtils.isEmpty(param.getLabel()), TbRegisterInfoEntity::getOperationLabel, param.getLabel());
        }

        queryWrapper.like(!StringUtils.isEmpty(param.getCompanyName()), TbRegisterInfoEntity::getCompanyName, param.getCompanyName());

        queryWrapper.eq(TbRegisterInfoEntity::getAuditStatus, 1);
        queryWrapper.orderByDesc(TbRegisterInfoEntity::getAuditTime);
        queryWrapper.eq(TbRegisterInfoEntity::getBlacklist, false);

        return super.baseMapper.selectPage(
                new Page(param.getPageNo(), param.getPageSize()),
                queryWrapper
        );
    }

    /**
     * 查询项目方的所有企业
     *
     * @return
     */
    public List<TbRegisterInfoEntity> queryProjectCompany() {
        return super.baseMapper.selectList(
                new LambdaQueryWrapper<TbRegisterInfoEntity>()
                        .isNotNull(TbRegisterInfoEntity::getProjectLabel)
                        .eq(TbRegisterInfoEntity::getAuditStatus, 1)
        );
    }

}
