package com.yjtech.wisdom.tourism.bigscreen.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 注册信息
 *
 * @author MJ~
 * @since 2022-03-02
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AuditCompanyParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 审核备注
     */
    @NotNull(message = "审核备注不能为空")
    private String auditComment;


    /**
     * 审核状态 0.审核中 1.通过 2.驳回
     */
    @NotNull(message = "审核状态不能为空")
    private Integer auditStatus;


}
