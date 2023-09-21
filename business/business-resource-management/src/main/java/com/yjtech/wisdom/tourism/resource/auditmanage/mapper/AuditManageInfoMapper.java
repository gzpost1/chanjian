package com.yjtech.wisdom.tourism.resource.auditmanage.mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.resource.auditmanage.entity.AuditManageInfo;

/**
 * @author songjun
 * @since 2023/9/18
 */
public interface AuditManageInfoMapper extends BaseMapper<AuditManageInfo> {
    int updateBySourceId(@Param("updated")AuditManageInfo updated, @Param("processId")Long processId);

    int insertOrUpdate(AuditManageInfo auditManageInfo);
}