package com.yjtech.wisdom.tourism.resource.auditmanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.resource.auditmanage.entity.AuditManageLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author songjun
 * @since 2023/9/8
 */
public interface AuditManageLogMapper extends BaseMapper<AuditManageLog> {
    List<AuditManageLog> auditLogList(@Param("id") Long id);
}