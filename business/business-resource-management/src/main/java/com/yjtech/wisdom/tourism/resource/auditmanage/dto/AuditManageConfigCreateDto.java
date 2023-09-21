package com.yjtech.wisdom.tourism.resource.auditmanage.dto;

import com.yjtech.wisdom.tourism.resource.auditmanage.entity.AuditManageProcess;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author songjun
 * @since 2023/9/11
 */
@Data
public class AuditManageConfigCreateDto {

    /**
     * 名称
     */
    @NotEmpty(message = "名称不能为空")
    private String name;

    /**
     * 流程配置
     */
    private List<AuditManageProcess> processList;
}
