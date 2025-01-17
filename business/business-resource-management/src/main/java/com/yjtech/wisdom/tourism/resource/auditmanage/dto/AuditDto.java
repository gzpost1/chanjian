package com.yjtech.wisdom.tourism.resource.auditmanage.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author songjun
 * @since 2023/9/11
 */
@Data
public class AuditDto {
    /**
     * 审批名称
     */
    private String auditName;
    /**
     * 审批数据的id
     */
    @JsonProperty("id")
    private Long sourceId;
    /**
     * 操作 1-通过 2-不通过
     */
    @Max(2)
    @Min(1)
    private Integer status;
    /**
     * 审核意见
     */
    private String text;
}
