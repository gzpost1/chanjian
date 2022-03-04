package com.yjtech.wisdom.tourism.bigscreen.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 审核记录
 *
 * @author MJ~
 * @since 2022-03-04
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbAuditInfoParam extends PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 企业id
     */
    private Long companyId;

    /**
     * 审核意见
     */
    private String auditComment;

    private Long createUser;

    private Long updateUser;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 审核状态 0.待审核 1.通过 2.驳回
     */
    private Integer auditStatus;


}
