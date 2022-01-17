package com.yjtech.wisdom.tourism.command.entity.plan;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 应急预案
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_emergency_plan")
public class EmergencyPlanEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("id")
    private Long id;


    /**
     * 名称
     */
    private String name;


    /**
     * 类型
     */
    private Long type;

    /**
     * 类型名称
     */
    @TableField(exist = false)
    private String typeName;

    /**
     * 发布机构
     */
    private String releaseInstitution;


    /**
     * 发布日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;


    /**
     * 发布内容
     */
    private String content;

}
