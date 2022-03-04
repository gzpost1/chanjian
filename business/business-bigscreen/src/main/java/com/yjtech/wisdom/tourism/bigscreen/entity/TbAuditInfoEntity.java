package com.yjtech.wisdom.tourism.bigscreen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.NotNull;

import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.enums.BeanValidationGroup;
/**
 * 审核记录
 *
 * @author MJ~
 * @since 2022-03-03
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_audit_info")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TbAuditInfoEntity extends Model<TbAuditInfoEntity> {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 企业id
     */
    @NotNull(message = "企业id不能为空")
    private Long companyId;

    /**
     * 审核意见
     */
    private String auditComment;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    @TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 审核状态 0.待审核 1.通过 2.驳回
     */
    private Integer auditStatus;

    public static final String ID = "id";

    public static final String COMPANY_ID = "company_id";

    public static final String AUDIT_COMMENT = "audit_comment";

    public static final String CREATE_USER = "create_user";

    public static final String UPDATE_USER = "update_user";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
