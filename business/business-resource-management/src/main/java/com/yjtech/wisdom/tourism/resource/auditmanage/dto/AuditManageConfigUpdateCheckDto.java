package com.yjtech.wisdom.tourism.resource.auditmanage.dto;

import lombok.Data;

import java.util.List;

/**
 * @author songjun
 * @since 2023/9/13
 */
@Data
public class AuditManageConfigUpdateCheckDto {
    private List<Long> userIds;
}
