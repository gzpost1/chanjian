package com.yjtech.wisdom.tourism.resource.auditmanage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.resource.auditmanage.entity.AuditManageInfo;
import com.yjtech.wisdom.tourism.resource.auditmanage.mapper.AuditManageInfoMapper;
/**
 *
 * @author songjun
 * @since 2023/9/8
 */
@Service
public class AuditManageInfoService extends ServiceImpl<AuditManageInfoMapper, AuditManageInfo> {
    public int insertOrUpdate(AuditManageInfo auditManageInfo) {
        return baseMapper.insertOrUpdate(auditManageInfo);
    }

    public int updateByProcessId(AuditManageInfo updated, Long processId) {
        return baseMapper.updateByProcessId(updated, processId);
    }
}
