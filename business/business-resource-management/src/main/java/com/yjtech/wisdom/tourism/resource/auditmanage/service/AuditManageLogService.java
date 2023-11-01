package com.yjtech.wisdom.tourism.resource.auditmanage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.resource.auditmanage.entity.AuditManageLog;
import com.yjtech.wisdom.tourism.resource.auditmanage.mapper.AuditManageLogMapper;
/**
 *
 * @author songjun
 * @since 2023/9/8
 */
@Service
public class AuditManageLogService extends ServiceImpl<AuditManageLogMapper, AuditManageLog> {

    public List<AuditManageLog> auditLogList(Long id) {
        return baseMapper.auditLogList(id);
    }

    public int updateByProcessIdAndSourceId(AuditManageLog updated, Long processId, Long sourceId) {
        return baseMapper.updateByProcessIdAndSourceId(updated, processId, sourceId);
    }

    public int updateStatusAndTextBySourceId(Integer updatedStatus, String updatedText, Long sourceId) {
        return baseMapper.updateStatusAndTextBySourceId(updatedStatus, updatedText, sourceId);
    }
}
