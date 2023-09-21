package com.yjtech.wisdom.tourism.resource.auditmanage.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author songjun
 * @since 2023/9/11
 */
@Data
public class AuditManageConfigUpdateDto extends AuditManageConfigCreateDto {
    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;
}
