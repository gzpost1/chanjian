package com.yjtech.wisdom.tourism.resource.auditmanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.resource.auditmanage.entity.AuditManageInfo;

/**
 * @author songjun
 * @since 2023/9/18
 */
public interface AuditManageInfoMapper extends BaseMapper<AuditManageInfo> {
    int insertOrUpdate(AuditManageInfo auditManageInfo);
}