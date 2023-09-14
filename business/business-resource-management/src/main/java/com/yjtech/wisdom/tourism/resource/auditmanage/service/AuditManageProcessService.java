package com.yjtech.wisdom.tourism.resource.auditmanage.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.resource.auditmanage.entity.AuditManageProcess;
import com.yjtech.wisdom.tourism.resource.auditmanage.mapper.AuditManageProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author songjun
 * @since 2023/9/8
 */
@Service
public class AuditManageProcessService extends ServiceImpl<AuditManageProcessMapper, AuditManageProcess> {

    public AuditManageProcess nextProcess(Long sourceId){
        return baseMapper.nextProcess(sourceId);
    }


    public int insertList(List<AuditManageProcess> list) {
        return baseMapper.insertList(list);
    }

    public int deleteByConfigId(Long configId) {
        return baseMapper.deleteByConfigId(configId);
    }
}
