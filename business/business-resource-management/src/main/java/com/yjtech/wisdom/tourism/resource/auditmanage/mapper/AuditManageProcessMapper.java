package com.yjtech.wisdom.tourism.resource.auditmanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.resource.auditmanage.entity.AuditManageProcess;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author songjun
 * @since 2023/9/8
 */
public interface AuditManageProcessMapper extends BaseMapper<AuditManageProcess> {
    AuditManageProcess nextProcess(@Param("process") AuditManageProcess process);

    int insertList(@Param("list") List<AuditManageProcess> list);

    int deleteByConfigId(@Param("configId") Long configId);


    AuditManageProcess firstProcess(@Param("auditName") String auditName);

    AuditManageProcess getBySourceId(@Param("sourceId") Long sourceId);

    List<AuditManageProcess> auditingProcessList(@Param("configId") Long configId);
}