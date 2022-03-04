package com.yjtech.wisdom.tourism.bigscreen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbAuditInfoEntity;
import com.yjtech.wisdom.tourism.bigscreen.mapper.TbAuditInfoMapper;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 审核记录 服务实现类
 *
 * @author Mujun
 * @since 2022-03-03
 */
@Service
public class TbAuditInfoService extends BaseMybatisServiceImpl<TbAuditInfoMapper, TbAuditInfoEntity>  {
    public List<TbAuditInfoEntity> queryAuditRecord(Long id) {
        QueryWrapper<TbAuditInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TbAuditInfoEntity.COMPANY_ID, id);
        queryWrapper.orderByDesc(TbAuditInfoEntity.CREATE_TIME);
        List<TbAuditInfoEntity> list = baseMapper.selectList(queryWrapper);
        return list;
    }

}
